package com.dorea.petgree.pet.specification;

import com.dorea.petgree.pet.domain.Pet;
import com.dorea.petgree.pet.domain.PetColor;
import com.dorea.petgree.pet.domain.PetGender;
import com.dorea.petgree.pet.domain.PetPelo;
import com.dorea.petgree.pet.domain.PetSize;
import com.dorea.petgree.pet.domain.PetStatus;
import com.dorea.petgree.pet.domain.PetType;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.util.GeometricShapeFactory;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class PetSpecification {
	public static Specification<Pet> byFilter(PetFilter filter) {
		return new Specification<Pet>() {
			@Override
			public Predicate toPredicate(Root<Pet> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				if (filter.getType() != null) {
					PetType.TypePet typePet = PetType.TypePet.getType(filter.getType().toUpperCase());
					if (typePet != null) {
						PetType petType = new PetType();
						petType.setId(typePet.getType());
						predicates.add(cb.equal(root.get("type"),petType));
					}
				}

				if (!ObjectUtils.isEmpty(filter.getColors())) {
					List<Predicate> colorsPredicate = new ArrayList<>();
					for (String color: filter.getColors()) {
						System.out.println(color);
						PetColor.ColorPet colorPet = PetColor.ColorPet.getColor(color.toUpperCase());
						if (colorPet != null) {
							PetColor petColor = new PetColor();
							petColor.setId(colorPet.getColor());

							Expression<List<PetColor>> cores = root.get("colors");

							colorsPredicate.add(cb.isMember(petColor,cores));
						}
					}
					predicates.add(cb.or(colorsPredicate.toArray(new Predicate[colorsPredicate.size()])));
				}

				if (!ObjectUtils.isEmpty(filter.getGender())) {
					PetGender.GenderPet genderPet = PetGender.GenderPet.getGender(filter.getGender().toUpperCase());
					if (genderPet != null) {
						PetGender petGender = new PetGender();
						petGender.setId(genderPet.getGender());
						predicates.add(cb.equal(root.get("gender"),petGender));
					}
				}

				if (!ObjectUtils.isEmpty(filter.getSize())) {
					PetSize.SizePet sizePet = PetSize.SizePet.getSize(filter.getSize().toUpperCase());
					if (sizePet != null) {
						PetSize petSize = new PetSize();
						petSize.setId(sizePet.getSize());
						predicates.add(cb.equal(root.get("size"),petSize));
					}
				}

				if (!ObjectUtils.isEmpty(filter.getStatus())) {
					PetStatus.StatusPet statusPet = PetStatus.StatusPet.getStatus(filter.getStatus().toUpperCase());
					if (statusPet != null) {
						PetStatus petStatus = new PetStatus();
						petStatus.setId(statusPet.getStatus());
						predicates.add(cb.equal(root.get("status"),petStatus));
					}
				}

				if (!ObjectUtils.isEmpty(filter.getPelo())) {
					PetPelo.PeloPet peloPet = PetPelo.PeloPet.getPelo(filter.getPelo().toUpperCase());
					if (peloPet != null) {
						PetPelo petPelo= new PetPelo();
						petPelo.setId(peloPet.getPelo());
						predicates.add(cb.equal(root.get("pelo"),petPelo));
					}
				}
				/**
				 *  Filtro de raça é mais imprevisível, a busca vai ser menos restritiva.
				 *  Buscar cada palavra inserida (separar por espaço, vírgula, ponto e vírgula, ponto e hífen).
				 *  Retornar resultados que contenham cada uma dessas palavras
				 */
				if (!ObjectUtils.isEmpty(filter.getRaca())) {
					String[] racas = filter.getRaca().split("[ .,;-]");
					List<Predicate> racesPredicate = new ArrayList<>();
					for (String raca: racas) {
						if (!ObjectUtils.isEmpty(raca)) {
							racesPredicate.add(cb.like(root.get("raca"), "%" + raca + "%"));
							if (!Normalizer.isNormalized(raca, Normalizer.Form.NFD)) {
								String normalized = Normalizer.normalize(raca, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
								racesPredicate.add(cb.like(root.get("raca"), "%" + normalized + "%")); // Sem os acentos
							}
						}
					}
					predicates.add(cb.or(racesPredicate.toArray(new Predicate[racesPredicate.size()])));
				}


				if (!ObjectUtils.isEmpty(filter.getLat()) && !ObjectUtils.isEmpty(filter.getLon())) {
					Geometry area;
					if (!ObjectUtils.isEmpty(filter.getRadius())) {
						area = createCircle(filter.getLon(),filter.getLat(),filter.getRadius());
						predicates.add(new WithinPredicate((CriteriaBuilderImpl) cb, root.get("geom"), area));
					} else {
						area = createCircle(filter.getLat(),filter.getLon(),10.0);
						predicates.add(new WithinPredicate((CriteriaBuilderImpl) cb, root.get("geom"), area));
					}
				}

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	private static Geometry createCircle(double x, double y, final double RADIUS) {

		GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
		shapeFactory.setNumPoints(32);
		shapeFactory.setCentre(new Coordinate(x, y));//there are your coordinates
		shapeFactory.setSize(RADIUS * 2);//this is how you set the radius
		Geometry shape = shapeFactory.createCircle();
		shape.setSRID(4326);
		return shape;
	}

}
