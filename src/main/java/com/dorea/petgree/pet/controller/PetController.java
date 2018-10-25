package com.dorea.petgree.pet.controller;

import com.dorea.petgree.pet.controller.converter.PetConverter;
import com.dorea.petgree.pet.domain.Pet;
import com.dorea.petgree.pet.domain.PetModel;
import com.dorea.petgree.pet.domain.PetStatus;
import com.dorea.petgree.pet.domain.json.Photo;
import com.dorea.petgree.pet.domain.json.User;
import com.dorea.petgree.pet.exception.CreatorNotFoundException;
import com.dorea.petgree.pet.exception.IdForbiddenException;
import com.dorea.petgree.pet.exception.InvalidInputException;
import com.dorea.petgree.pet.exception.NeedCreatorIdException;
import com.dorea.petgree.pet.exception.NotificationMisuseException;
import com.dorea.petgree.pet.exception.OwnerNotFoundException;
import com.dorea.petgree.pet.exception.PetNotFoundException;
import com.dorea.petgree.pet.service.EmailService;
import com.dorea.petgree.pet.service.PetService;
import com.dorea.petgree.pet.specification.PetFilter;
import com.dorea.petgree.pet.validate.ValidatePet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/pets")
public class PetController implements WebMvcConfigurer {

    @Autowired
    private PetService petService;

    @Autowired
    private EmailService emailService;

	private final String adminEmail = "yago.dorea@gmail.com";

	private final String userApiUrl = "http://ec2-18-228-44-159.sa-east-1.compute.amazonaws.com:4243/users";
//	private final String userApiUrl = "http://localhost:4243/users";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Pet postPet(@RequestBody PetModel petModel) {
	    System.out.println("postPet invoked.");
	    Pet pet = PetConverter.toPet(petModel,null);

    	if (pet.getId() != null) {
            throw new IdForbiddenException();
        }
        if (!ValidatePet.isValid(pet)) {
            throw new InvalidInputException();
        }
        if (pet.getCreated_by() == null) {
    		throw new NeedCreatorIdException();
        } else {
    		// Procurar o usuário na API User
	        RestTemplate restTemplate = new RestTemplate();
	        try {
	        	User user = restTemplate.getForObject(userApiUrl + "/email/" + pet.getCreated_by(),User.class);
	        	if (pet.getStatus().getId() == PetStatus.StatusPet.getStatus("PERDIDO").getStatus()) {
	        		pet.setOwner_id(user.getId());
		        }

                Pet posted = petService.postPet(pet);

    	        // Adicionar o pet na lista owned do usuário
		        if (pet.getStatus().getId() == PetStatus.StatusPet.getStatus("PERDIDO").getStatus()
				        || pet.getStatus().getId() == PetStatus.StatusPet.getStatus("OK").getStatus()
				        || pet.getStatus().getId() == PetStatus.StatusPet.getStatus("QUER_CRUZAR").getStatus()) {
			        restTemplate.postForObject(userApiUrl + "/" + user.getId() + "/owned", posted.getId(),Void.class);
		        }

    	        return posted;
	        } catch (HttpClientErrorException error) {
		        throw new CreatorNotFoundException(error.getMessage());
	        }
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Pet> getPets(
    		@RequestParam(required = false) String type,
		    @RequestParam(required = false) String[] colors,
		    @RequestParam(required = false) String gender,
		    @RequestParam(required = false) String raca,
		    @RequestParam(required = false) String pelo,
		    @RequestParam(required = false) String size,
		    @RequestParam(required = false) String status,
		    @RequestParam(required = false) Double lat,
		    @RequestParam(required = false) Double lon,
		    @RequestParam(required = false) Double radius,
		    @RequestParam(required = false) Integer limit,
		    @RequestParam(required = false) Integer offset) {
    	if (ObjectUtils.isEmpty(limit) || limit < 1) {
    		limit = 10;
	    }
	    if (ObjectUtils.isEmpty(offset) || offset < 1) {
    		offset = 1;
	    }
	    Pageable pageable = PageRequest.of(offset - 1, limit);

        System.out.println("getPets invoked.");
	    PetFilter filter = new PetFilter();

	    filter.setAllFilters(type,colors,gender,raca,pelo,size,status,lat,lon,radius);

	    return petService.getByFilter(filter,pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Pet updatePet(@PathVariable Long id,
                         @RequestBody PetModel petModel) {
	    System.out.println("putPet invoked.");

	    Optional<Pet> pet = petService.getPetById(id);
	    if (!pet.isPresent()) {
		    throw new PetNotFoundException(id);
	    }

	    Pet converted = PetConverter.toPet(petModel, pet.get());

	    return petService.postPet(converted);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable Long id) {
	    System.out.println("deletePet invoked.");
	    Optional<Pet> pet = petService.getPetById(id);
	    if (pet.isPresent()) {
	        petService.deletePet(id);
	        if (Objects.nonNull(pet.get().getOwner_id())) {
		        // Procurar o usuário na API User
		        RestTemplate restTemplate = new RestTemplate();
		        try {
			        User user = restTemplate.getForObject(userApiUrl + "/" + String.valueOf(pet.get().getOwner_id()), User.class);
			        Set<Long> owned = new HashSet<>(user.getOwned());
			        if (owned.contains(pet.get().getId())) {
			        	owned.remove(pet.get().getId());
			        }
			        user.setOwned(owned);
			        restTemplate.put(userApiUrl + "/" + user.getId(), user);
		        } catch (HttpClientErrorException e) {
		        	throw new HttpClientErrorException(e.getStatusCode());
		        }
	        }
        }
        else {
	        throw new PetNotFoundException(id);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Pet getPet(@PathVariable("id") Long id) {
	    System.out.println("getPet invoked.");
	    Optional<Pet> pet = petService.getPetById(id);
        if (!pet.isPresent()) {
            throw new PetNotFoundException(id);
        }
        return pet.get();
    }

    @RequestMapping(value = "/{id}/fotos", method = RequestMethod.PATCH)
	public Pet addPhoto(@PathVariable("id") Long id,
                        @RequestBody Photo photo) {
    	System.out.println("addPhoto invoked.");
	    Optional<Pet> pet = petService.getPetById(id);
	    if (!pet.isPresent()) {
    		throw new PetNotFoundException(id);
	    }
	    petService.addPhoto(photo.getImageUrl(),id);
    	return pet.get();
    }

    @RequestMapping(value = "/{id}/notification", method = RequestMethod.POST)
    public void notificateOwner(@PathVariable("id") Long id,
                                @RequestBody User user) {
	    System.out.println("notificateOwner invoked.");
    	Optional<Pet> pet = petService.getPetById(id);
    	if (!pet.isPresent()) {
    		throw new PetNotFoundException(id);
	    }
	    if (!pet.get().getStatus().getDescription().equalsIgnoreCase("PERDIDO")) {
    		System.out.println(pet.get().getStatus().getDescription());
    		throw new NotificationMisuseException(pet.get().getStatus().getDescription());
	    }

	    Long ownerId = pet.get().getOwner_id();

    	if (ObjectUtils.isEmpty(ownerId)) {
    		throw new OwnerNotFoundException();
	    }

	    // Procurar o usuário na API User
	    RestTemplate restTemplate = new RestTemplate();
	    try {
		    User owner = restTemplate.getForObject(userApiUrl + "/" + ownerId, User.class);
		    String ownerEmail = owner.getEmail();

		    String phones = "";
		    if (!ObjectUtils.isEmpty(user.getTelefones())) {
		    	phones += "Você também pode contatá-lo através dos telefones<bold>";
		    	for(String telefone : user.getTelefones()) {
		    		phones += " " + telefone;
			    }
			    phones += "</bold>";
		    }

		    emailService.sendEmail(
		    		ownerEmail,
				    adminEmail,
				    "Pet encontrado!",
				    "Boas notícias! O seu pet <b>" + pet.get().getName() + "</b> foi encontrado!<br/><br/>"
					    + "O usuário <b>" + user.getAvatar().getName() + "</b> acabou de reportar que o encontrou.<br/><br/>"
					    + "Entre em contato através do e-mail <b>" + user.getEmail() + "</b>.<br/>" + phones
		    );
	    } catch (HttpClientErrorException error) {
		    throw new OwnerNotFoundException();
	    }
		System.out.println("notification sent.");
    }
}
