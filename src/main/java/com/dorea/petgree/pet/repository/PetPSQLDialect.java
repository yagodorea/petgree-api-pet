package com.dorea.petgree.pet.repository;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.spatial.dialect.postgis.PostgisPG95Dialect;

public class PetPSQLDialect extends PostgisPG95Dialect {

	public PetPSQLDialect() {
		super();

		registerFunction("gc_dist",
				new StandardSQLFunction("gc_dist"));
	}
}
