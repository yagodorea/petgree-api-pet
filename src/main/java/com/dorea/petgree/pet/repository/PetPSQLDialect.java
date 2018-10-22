package com.dorea.petgree.pet.repository;

import org.hibernate.dialect.PostgreSQL94Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;

public class PetPSQLDialect extends PostgreSQL94Dialect {

	public PetPSQLDialect() {
		super();

		registerFunction("gc_dist",
				new StandardSQLFunction("gc_dist"));
	}
}
