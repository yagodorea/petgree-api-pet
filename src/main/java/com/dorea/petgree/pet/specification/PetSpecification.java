package com.dorea.petgree.pet.specification;

import com.dorea.petgree.pet.domain.Pet;
import com.dorea.petgree.pet.domain.PetColor;
import com.dorea.petgree.pet.domain.PetGender;
import com.dorea.petgree.pet.domain.PetPelo;
import com.dorea.petgree.pet.domain.PetSize;
import com.dorea.petgree.pet.domain.PetStatus;
import com.dorea.petgree.pet.domain.PetType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Predicate;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class PetSpecification {

	public static Specification<Pet> hasType(PetType type) {
		return (pet, cq, cb) -> cb.equal(pet.get("type"),type);
	}

	public static Specification<Pet> hasRaca(String raca) {
		return (pet, cq, cb) -> cb.equal(pet.get("raca"),"%" + raca + "%");
	}

	public static Specification<Pet> hasGender(PetGender gender) {
		return (pet, cq, cb) -> cb.equal(pet.get("gender"),gender);
	}

	public static Specification<Pet> hasSize(PetSize size) {
		return (pet, cq, cb) -> cb.equal(pet.get("size"),size);
	}

	public static Specification<Pet> hasStatus(PetStatus status) {
		return (pet, cq, cb) -> cb.equal(pet.get("status"),status);
	}

	public static Specification<Pet> hasPelo(PetPelo pelo) {
		return (pet, cq, cb) -> cb.equal(pet.get("pelo"),pelo);
	}

	public static Specification<Pet> byColor(PetColor color) {
		return (Root<Pet> pet, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
			if (color != null) {

				Subquery<Pet> sq = cq.subquery(Pet.class);

				Root<PetColor> petcolor = cq.from(PetColor.class);

				Join<PetColor,Pet> sqPet = petcolor.join("pet_id");

				sq.select(sqPet).where(cb.equal(petcolor.get("colors_id_color"),color.getId()));

				return cb.in(pet).value(sq);
			} else {
				return cb.in(pet).value(new Pet());
			}
		};
	}

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

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

}
