package com.musala.drone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_medication")
public class Medication {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "med_id")
	private Long id;
    @Column(name = "med_code")
    public String code;
    @Column(name = "med_name")
    public String name;
    @Column(name = "med_weight")
    public double weight;
    @Column(name = "med_image")
    public String med_image;
    @JsonIgnore
    @ManyToOne
    private Drone drone;
}
