package com.dorea.petgree.pet.repository;

import com.dorea.petgree.pet.domain.Pet;
import com.dorea.petgree.pet.domain.PetType;

import java.util.List;

public interface PetRepositoryCustom {
	List<Pet> findPetsByType(PetType type);
}
