package com.dorea.petgree.pet.repository;

import com.dorea.petgree.pet.domain.Pet;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PetRepository extends PagingAndSortingRepository<Pet,Long>, PetRepositoryCustom, JpaSpecificationExecutor<Pet> {
//	@Query(value = "SELECT * FROM Pet p INNER JOIN gc_dist(:lat,:lon) USING (id) ORDER BY distance;", nativeQuery = true)
//	List<Pet> findByDistance(Double lat, Double lon, Specification<Pet> specification);
}
