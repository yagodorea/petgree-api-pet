package com.dorea.petgree.pet.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "pet_type_info")
public class PetType implements Serializable {

    public enum TypePet {
        NULL(0), CACHORRO(1), GATO(2), ROEDOR(3), CAVALO(4), REPTIL(5), PASSARO(6), OUTRO(7);

        TypePet(int type) { this.type = type; }

        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public static TypePet getType(String acronym) {
            for (TypePet type : values()) {
                if (type.toString().equalsIgnoreCase(acronym)) {
                    return type;
                }
            }
            return null;
        }
    }

    @Id
    @Column(name="id_type")
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
