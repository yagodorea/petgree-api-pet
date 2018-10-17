package com.dorea.petgree.pet.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "pet_pelo_info")
public class PetPelo implements Serializable {

	public enum PeloPet {
		NENHUM(0), POUCO(1), MÉDIO(2), MUITO(3);

		PeloPet(int pelo) { this.pelo = pelo; }

		private int pelo;

		public int getPelo() { return pelo; }

		public void setPelo(int pelo) { this.pelo = pelo; }

		public static PeloPet getPelo(String acronym) {
			for (PeloPet pelo : values()) {
				if (pelo.toString().equalsIgnoreCase(acronym)) {
					return pelo;
				}
			}
			return null;
		}
	}

	@Id
	@Column(name = "id_pelo")
	private Integer id;

	@Column(name = "description")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
