package com.dorea.petgree.pet.service.impl;

import com.dorea.petgree.pet.domain.Pet;
import com.dorea.petgree.pet.service.PetService;
import com.dorea.petgree.pet.repository.PetRepository;
import com.dorea.petgree.pet.specification.PetFilter;
import com.dorea.petgree.pet.specification.PetSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Override
    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }

    @Override
    public Pet postPet(Pet pet) { return petRepository.save(pet); }

    @Override
    public void deletePet(Long id) {
	    Optional<Pet> pet = getPetById(id);
        if (pet.isPresent()) {
            petRepository.deleteById(id);
        }
    }

	@Override
	public Pet addPhoto(String image_url, Long id) {
		Optional<Pet> pet = getPetById(id);
		if (pet.isPresent()) {
			Set<String> fotos = pet.get().getFotos() != null ? pet.get().getFotos() : new HashSet<>();
			fotos.add(image_url);
		}
		return petRepository.save(pet.get());
	}

	@Override
	public List<Pet> getByFilter(PetFilter filter, Pageable pageable) {
    	Page<Pet> result = petRepository.findAll(PetSpecification.byFilter(filter),pageable);
		return result.getContent();
	}
}
