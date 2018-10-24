package com.dorea.petgree.pet.controller.converter;

import com.dorea.petgree.pet.domain.Pet;
import com.dorea.petgree.pet.domain.PetColor;
import com.dorea.petgree.pet.domain.PetColor.ColorPet;
import com.dorea.petgree.pet.domain.PetGender;
import com.dorea.petgree.pet.domain.PetGender.GenderPet;
import com.dorea.petgree.pet.domain.PetModel;
import com.dorea.petgree.pet.domain.PetPelo;
import com.dorea.petgree.pet.domain.PetPelo.PeloPet;
import com.dorea.petgree.pet.domain.PetSize;
import com.dorea.petgree.pet.domain.PetStatus;
import com.dorea.petgree.pet.domain.PetType;
import com.dorea.petgree.pet.domain.PetType.TypePet;
import com.dorea.petgree.pet.domain.PetSize.SizePet;
import com.dorea.petgree.pet.domain.PetStatus.StatusPet;
import com.dorea.petgree.pet.repository.serializer.JsonToPointDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class PetConverter {

	public static Pet toPet(@NotNull PetModel petModel, Pet pet) {
		if (pet == null) {
			pet = new Pet();
		}

		if (petModel.getId() != null) {
			pet.setId(petModel.getId());
		}

		if (!ObjectUtils.isEmpty(petModel.getName())) {
			pet.setName(petModel.getName());
		} else if (ObjectUtils.isEmpty(pet.getName())) {
			pet.setName("Sem nome");
		}

		if (!ObjectUtils.isEmpty(petModel.getType())) {
			if (!Normalizer.isNormalized(petModel.getType(), Normalizer.Form.NFD))  {
				petModel.setType(Normalizer.normalize(petModel.getType(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]",""));
			}
			TypePet typePet = TypePet.getType(petModel.getType().toUpperCase());
			if (typePet != null) {
				PetType petType = new PetType();
				petType.setId(typePet.getType());
				pet.setType(petType);
			}
		}

		if (!ObjectUtils.isEmpty(petModel.getRaca())) {
			pet.setRaca(petModel.getRaca());
		}

		if (!ObjectUtils.isEmpty(petModel.getGender())) {
			if (!Normalizer.isNormalized(petModel.getGender(), Normalizer.Form.NFD))  {
				petModel.setGender(Normalizer.normalize(petModel.getGender(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]",""));
			}
			GenderPet genderPet = GenderPet.getGender(petModel.getGender().toUpperCase());
			if (genderPet != null) {
				PetGender petGender = new PetGender();
				petGender.setId(genderPet.getGender());
				pet.setGender(petGender);
			}
		}

		if (!ObjectUtils.isEmpty(petModel.getSize())) {
			if (!Normalizer.isNormalized(petModel.getSize(), Normalizer.Form.NFD))  {
				petModel.setSize(Normalizer.normalize(petModel.getSize(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]",""));
			}
			SizePet sizePet = SizePet.getSize(petModel.getSize().toUpperCase());
			if (sizePet != null) {
				PetSize petSize = new PetSize();
				petSize.setId(sizePet.getSize());
				pet.setSize(petSize);
			}
		}

		if (!ObjectUtils.isEmpty(petModel.getPelo())) {
			if (!Normalizer.isNormalized(petModel.getPelo(), Normalizer.Form.NFD))  {
				petModel.setPelo(Normalizer.normalize(petModel.getPelo(), Normalizer.Form.NFD));
			}
			PeloPet peloPet = PeloPet.getPelo(petModel.getPelo().toUpperCase());
			if (peloPet != null) {
				PetPelo petPelo= new PetPelo();
				petPelo.setId(peloPet.getPelo());
				pet.setPelo(petPelo);
			}
		}

		if (!ObjectUtils.isEmpty(petModel.getColors())) {
			Set<PetColor> colors = new HashSet<>();
			for (String color : petModel.getColors()) {
				ColorPet colorPet = ColorPet.getColor(color.toUpperCase());
				if (colorPet != null) {
					PetColor petColor = new PetColor();
					petColor.setId(colorPet.getColor());
					colors.add(petColor);
				}
			}
			pet.setColors(colors);
		}

		if (!ObjectUtils.isEmpty(petModel.getStatus())) {
			StatusPet statusPet = StatusPet.getStatus(petModel.getStatus().toUpperCase());
			if (statusPet != null) {
				PetStatus petStatus = new PetStatus();
				petStatus.setId(statusPet.getStatus());
				pet.setStatus(petStatus);
			}
		} else if (ObjectUtils.isEmpty(pet.getStatus())) { // Se não vier com status, é um animal perdido
			PetStatus petStatus = new PetStatus();
			petStatus.setId(0);
			pet.setStatus(petStatus);
		}

		if (!ObjectUtils.isEmpty(petModel.getDescription())) {
			if (petModel.getDescription().length() > 255) {
				pet.setDescription(petModel.getDescription().substring(0,255));
			} else {
				pet.setDescription(petModel.getDescription());
			}
		} else {
			pet.setDescription("Sem descrição.");
		}

		if (!ObjectUtils.isEmpty(petModel.getImage_url())) {
			pet.setImage_url(petModel.getImage_url());
		} else {
			pet.setImage_url("https://api.adorable.io/avatars/200/" + UUID.randomUUID());
		}

		if (petModel.getLat() != null) {
			pet.setLat(petModel.getLat());
		}

		if (petModel.getLon() != null) {
			pet.setLon(petModel.getLon());
		}

		if (!ObjectUtils.isEmpty(petModel.getFotos())) {
			pet.setFotos(petModel.getFotos());
		}

		if (Objects.nonNull(petModel.getLat()) && Objects.nonNull(petModel.getLon())) {
			GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

			// LEMBRAR QUE AS COORDENADAS SÃO INVERTIDAS NO POSTGIS!!!
			Point point = geometryFactory.createPoint(new Coordinate(petModel.getLon(), petModel.getLat()));

			pet.setGeom(point);
		}

		pet.setDt_created(Timestamp.from(Instant.now()));

		if (!ObjectUtils.isEmpty(petModel.getCreated_by())) {
			pet.setCreated_by(petModel.getCreated_by());
		}

		return pet;
	}
}
