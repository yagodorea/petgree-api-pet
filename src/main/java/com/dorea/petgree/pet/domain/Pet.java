package com.dorea.petgree.pet.domain;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

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

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "gender",referencedColumnName = "id_gender")
    private PetGender gender;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "size",referencedColumnName = "id_size")
    private PetSize size;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "color",referencedColumnName = "id_color")
    private PetColor color;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "status",referencedColumnName = "id_status")
    private PetStatus status;

    @Column(name = "spots")
    private boolean spots;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "ong_email")
    private String ong_email;

    @Column(name = "lat")
    private Float lat;

	@Column(name = "lon")
	private Float lon;

	@Column(name = "dt_created")
	private Timestamp dt_created;

	@Column(name = "created_by")
	private String created_by;

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


    public PetColor getColor() {
        return color;
    }

    public void setColor(PetColor color) {
        this.color = color;
    }

	public PetStatus getStatus() { return status; }

	public void setStatus(PetStatus status) { this.status = status; }

	public boolean isSpots() {
		return spots;
	}

    public void setSpots(boolean spots) {
        this.spots = spots;
    }

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

	public String getOng_email() {
		return ong_email;
	}

	public void setOng_email(String ong_email) {
		this.ong_email = ong_email;
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
