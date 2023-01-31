package com.musala.drone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.musala.drone.controller", "com.musala.drone.service"})
@EntityScan("com.musala.drone.entity")
@EnableJpaRepositories("com.musala.drone.repository")
public class DroneserviceapiApplication {

	public static void main(String[] args) {

		SpringApplication.run(DroneserviceapiApplication.class, args);
	}

}
