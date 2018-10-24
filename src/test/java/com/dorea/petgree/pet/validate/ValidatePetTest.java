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
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ValidatePetTest {

	@Test
	public void ValidateValidPet() {
		Pet pet = new Pet();

		PetColor color = new PetColor();
		color.setId(ColorPet.BRANCO.getColor());
		Set<PetColor> colors = new HashSet<>();
		colors.add(color);
		pet.setColors(colors);

		PetGender gender = new PetGender();
		gender.setId(GenderPet.MACHO.getGender());
		pet.setGender(gender);

		PetType type = new PetType();
		type.setId(TypePet.CACHORRO.getType());
		pet.setType(type);

		PetSize size = new PetSize();
		size.setId(SizePet.GRANDE.getSize());
		pet.setSize(size);

		assertTrue(ValidatePet.isValid(pet));
	}
}
