package com.dorea.petgree.pet.validate;

import com.dorea.petgree.pet.domain.Pet;

public class ValidatePet {
    public static boolean isValid(Pet pet) {
        return (pet.getType() != null
		        && pet.getCreated_by() != null);
    }
}
