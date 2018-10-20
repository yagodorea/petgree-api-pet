package com.dorea.petgree.pet.service;

import com.dorea.petgree.pet.domain.Pet;
import com.dorea.petgree.pet.specification.PetFilter;

import java.util.List;
import java.util.Optional;

public interface PetService {

    List<Pet> getPets();

	Optional<Pet> getPetById(Long id);

    List<Pet> getByFilter(PetFilter filter);

    Pet postPet(Pet pet);

    Pet addPhoto(String image_url, Long id);

    void deletePet(Long id);
}
