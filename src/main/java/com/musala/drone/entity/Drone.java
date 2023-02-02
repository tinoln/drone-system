package com.musala.drone.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.musala.drone.enums.Model;
import com.musala.drone.enums.State;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_drone")
public class Drone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "drone_id")
	private Long id;
	@Column(name = "serial_no")
	private String serialNumber;
	@Column(name = "model")
	private Model model;
	@Column(name = "weight_limit")
	private double weightLimit;
	@Column(name = "battery")
	private Double battery;
	@Column(name = "state")
	private State state;
	@JsonIgnore
    @OneToMany(mappedBy = "drone")
    List<Medication> medications;
	
}
