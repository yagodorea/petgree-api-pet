package com.dorea.petgree.pet.service;

import com.dorea.petgree.pet.domain.Pet;
import com.dorea.petgree.pet.specification.PetFilter;

import java.util.List;

public interface PetService {

    List<Pet> getPets();

    Pet getPetById(Long id);

    List<Pet> getByFilter(PetFilter filter);

    Pet postPet(Pet pet);

    Pet addPhoto(String image_url, Long id);

    void deletePet(Long id);
}
