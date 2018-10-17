package com.dorea.petgree.pet.specification;

import com.dorea.petgree.pet.domain.Pet;
import com.dorea.petgree.pet.domain.PetColor;
import com.dorea.petgree.pet.domain.PetGender;
import com.dorea.petgree.pet.domain.PetPelo;
import com.dorea.petgree.pet.domain.PetSize;
import com.dorea.petgree.pet.domain.PetStatus;
import com.dorea.petgree.pet.domain.PetType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Predicate;
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

				if (filter.getColor() != null) {
					PetColor.ColorPet colorPet = PetColor.ColorPet.getColor(filter.getColor().toUpperCase());
					if (colorPet != null) {
						PetColor petColor = new PetColor();
						petColor.setId(colorPet.getColor());

						Expression<List<PetColor>> cores = root.get("colors");

						predicates.add(cb.isMember(petColor,cores));
					}
				}

				if (filter.getGender() != null) {
					PetGender.GenderPet genderPet = PetGender.GenderPet.getGender(filter.getGender().toUpperCase());
					if (genderPet != null) {
						PetGender petGender = new PetGender();
						petGender.setId(genderPet.getGender());
						predicates.add(cb.equal(root.get("gender"),petGender));
					}
				}

				if (filter.getSize() != null) {
					PetSize.SizePet sizePet = PetSize.SizePet.getSize(filter.getSize().toUpperCase());
					if (sizePet != null) {
						PetSize petSize = new PetSize();
						petSize.setId(sizePet.getSize());
						predicates.add(cb.equal(root.get("size"),petSize));
					}
				}

				if (filter.getStatus() != null) {
					PetStatus.StatusPet statusPet = PetStatus.StatusPet.getStatus(filter.getStatus().toUpperCase());
					if (statusPet != null) {
						PetStatus petStatus = new PetStatus();
						petStatus.setId(statusPet.getStatus());
						predicates.add(cb.equal(root.get("status"),petStatus));
					}
				}

				if (filter.getPelo() != null) {
					PetPelo.PeloPet peloPet = PetPelo.PeloPet.getPelo(filter.getPelo().toUpperCase());
					if (peloPet != null) {
						PetPelo petPelo= new PetPelo();
						petPelo.setId(peloPet.getPelo());
						predicates.add(cb.equal(root.get("pelo"),petPelo));
					}
				}

				if (filter.getRaca() != null) {
					predicates.add(cb.equal(root.get("raca"),"%" + filter.getRaca() + "%"));
				}

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

}
