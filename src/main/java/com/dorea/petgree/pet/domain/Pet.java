package com.dorea.petgree.pet.domain;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import com.dorea.petgree.pet.repository.serializer.JsonToPointDeserializer;
import com.dorea.petgree.pet.repository.serializer.PointToJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;

@Entity
@Table(name = "pets")
public class Pet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "type",referencedColumnName = "id_type")
    private PetType type;

    @Column(name = "raca")
    private String raca;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "gender",referencedColumnName = "id_gender")
    private PetGender gender;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "size",referencedColumnName = "id_size")
    private PetSize size;

    @ElementCollection
    @CollectionTable(name = "colors")
    private Set<PetColor> colors;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "pelo",referencedColumnName = "id_pelo")
	private PetPelo pelo;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "status",referencedColumnName = "id_status")
    private PetStatus status;

    @Column(name = "owner_id")
	private Long owner_id;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "lat")
    private Float lat;

	@Column(name = "lon")
	private Float lon;

	@ElementCollection
	@CollectionTable(name = "fotos")
	private Set<String> fotos;

	@Column(name = "dt_created")
	private Timestamp dt_created;

	@Column(name = "created_by")
	private String created_by;

	@JsonSerialize(using = PointToJsonSerializer.class)
	@JsonDeserialize(using = JsonToPointDeserializer.class)
	@Column(name = "geom", columnDefinition = "Geometry(Point,4326)")
	private Point geom;

	public Point getGeom() {
		return geom;
	}

	public void setGeom(Point geom) {
		this.geom = geom;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLon() {
		return lon;
	}

	public void setLon(Float lon) {
		this.lon = lon;
	}
	// Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public PetGender getGender() {
        return gender;
    }

    public void setGender(PetGender gender) {
        this.gender = gender;
    }

    public PetSize getSize() {
        return size;
    }

    public void setSize(PetSize size) {
        this.size = size;
    }

    public Set<PetColor> getColors() {
        return colors;
    }

    public void setColors(Set<PetColor> color) {
        this.colors = color;
    }

	public PetPelo getPelo() {
		return pelo;
	}

	public void setPelo(PetPelo pelo) {
		this.pelo = pelo;
	}

	public Long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
	}

	public Set<String> getFotos() {
		return fotos;
	}

	public void setFotos(Set<String> fotos) {
		this.fotos = fotos;
	}

	public PetStatus getStatus() { return status; }

	public void setStatus(PetStatus status) { this.status = status; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public Timestamp getDt_created() {
		return dt_created;
	}

	public void setDt_created(Timestamp dt_created) {
		this.dt_created = dt_created;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
}
