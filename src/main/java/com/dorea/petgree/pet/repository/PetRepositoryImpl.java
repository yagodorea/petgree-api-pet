package com.dorea.petgree.pet.repository;

import com.dorea.petgree.pet.domain.Pet;
//import com.dorea.petgree.pet.domain.PetColor;
import com.dorea.petgree.pet.domain.PetType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
//import java.util.ArrayList;
import java.util.List;

@Repository
public class PetRepositoryImpl implements PetRepositoryCustom {

	EntityManager em;

	@Override
	public List<Pet> findPetsByType(PetType type) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Pet> cq = cb.createQuery(Pet.class);

		Root<Pet> pet = cq.from(Pet.class);
		Predicate typePredicate = cb.equal(pet.get("type"), type);

		cq.where(typePredicate);

		TypedQuery<Pet> query = em.createQuery(cq);
		return query.getResultList();
	}
//
//	@Override
//	public List<Pet> findPetsByColor(PetColor color) {
//		ArrayList<PetColor> colors = ;
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaQuery<Pet> cq = cb.createQuery(Pet.class);
//
//		Root<Pet> pet = cq.from(Pet.class);
//		Predicate typePredicate = cb.equal(pet.get("type"), type);
//
//		cq.where(typePredicate);
//
//		TypedQuery<Pet> query = em.createQuery(cq);
//		return query.getResultList();
//	}
}
