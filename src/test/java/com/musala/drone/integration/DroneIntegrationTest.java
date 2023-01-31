package com.musala.drone.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import com.musala.drone.entity.Drone;
import com.musala.drone.enums.Model;
import com.musala.drone.repository.DroneRepository;

public class DroneIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	private String baseUrl = "http://localhost";
	
	private static RestTemplate restTemplate;
	
	@Autowired
	private DroneRepository droneRepository;
	
	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}
	
	@BeforeEach
	public void beforeSetup() {
		baseUrl = baseUrl + ":" + port + "/api/drone";
		
		Drone droneTestOne =  new Drone();
		
		Drone droneTestTwo =  new Drone();
		
		droneTestOne = droneRepository.save(droneTestOne);
		droneTestOne = droneRepository.save(droneTestTwo);
		
	}
	
	@AfterEach
	public void afterSetup() {
		droneRepository.deleteAll();
	}
	
	@Test
	void shouldRegisterDroneTest() {
		Drone droneOne = new Drone();
		droneOne.setSerialNumber(UUID.randomUUID().toString());
		droneOne.setModel(Model.Lightweight);
		
		Drone newDrone = restTemplate.postForObject(baseUrl, droneOne, Drone.class);
		
		assertThat(newDrone.getId()).isNotNull();
	}

}
