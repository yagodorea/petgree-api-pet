package com.dorea.petgree.pet.controller;

import com.dorea.petgree.pet.controller.converter.PetConverter;
import com.dorea.petgree.pet.domain.Pet;
import com.dorea.petgree.pet.domain.PetModel;
import com.dorea.petgree.pet.domain.json.User;
import com.dorea.petgree.pet.exception.CreatorNotFoundException;
import com.dorea.petgree.pet.exception.IdForbiddenException;
import com.dorea.petgree.pet.exception.InvalidInputException;
import com.dorea.petgree.pet.exception.NeedCreatorIdException;
import com.dorea.petgree.pet.exception.PetNotFoundException;
import com.dorea.petgree.pet.service.PetService;
import com.dorea.petgree.pet.validate.ValidatePet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController extends WebMvcConfigurerAdapter {

    @Autowired
    private PetService petService;

    private RestTemplate restTemplate;
    private final String userApiUrl = "http://ec2-18-228-44-159.sa-east-1.compute.amazonaws.com:4243/users";

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
	        restTemplate = new RestTemplate();
	        try {
	        	User response = restTemplate.getForObject(userApiUrl + "/email/" + String.valueOf(pet.getCreated_by()),User.class);
	        } catch (HttpClientErrorException error) {
	        	throw new CreatorNotFoundException(error.getMessage());
	        }
        }
        return petService.postPet(pet);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Pet> getPets() {
        System.out.println("getPets invoked.");
        return petService.getPets();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Pet updatePet(@PathVariable Long id,
                         @RequestBody PetModel petModel) {
	    System.out.println("putPet invoked.");

	    Pet pet = petService.getPetById(id);
	    if (pet == null) {
		    throw new PetNotFoundException(id);
	    }

	    pet = PetConverter.toPet(petModel, pet);

	    return petService.postPet(pet);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable Long id) {
	    System.out.println("deletePet invoked.");
	    if (petService.getPetById(id) != null) {

	        // TODO: Adicionar validação de ONG

	        petService.deletePet(id);
        }
        else {
	        throw new PetNotFoundException(id);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Pet getPet(@PathVariable("id") Long id) {
	    System.out.println("getPet invoked.");
	    Pet pet = petService.getPetById(id);
        if (pet == null) {
            throw new PetNotFoundException(id);
        }
        return pet;
    }
}