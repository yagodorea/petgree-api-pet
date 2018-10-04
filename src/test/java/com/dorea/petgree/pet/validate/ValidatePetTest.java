package com.dorea.petgree.pet.validate;

import com.dorea.petgree.pet.domain.Pet;
import com.dorea.petgree.pet.domain.PetColor;
import com.dorea.petgree.pet.domain.PetColor.ColorPet;
import com.dorea.petgree.pet.domain.PetGender;
import com.dorea.petgree.pet.domain.PetGender.GenderPet;
import com.dorea.petgree.pet.domain.PetSize;
import com.dorea.petgree.pet.domain.PetSize.SizePet;
import com.dorea.petgree.pet.domain.PetType;
import com.dorea.petgree.pet.domain.PetType.TypePet;
import org.junit.Assert;
import org.junit.Test;

public class ValidatePetTest {

	@Test
	public void ValidateValidPet() {
		Pet pet = new Pet();

		PetColor color = new PetColor();
		color.setId(ColorPet.BRANCO.getColor());
		pet.setColor(color);

		PetGender gender = new PetGender();
		gender.setId(GenderPet.MACHO.getGender());
		pet.setGender(gender);

		PetType type = new PetType();
		type.setId(TypePet.CACHORRO.getType());
		pet.setType(type);

		PetSize size = new PetSize();
		size.setId(SizePet.GRANDE.getSize());
		pet.setSize(size);

		Assert.assertTrue(ValidatePet.isValid(pet));
	}
}
