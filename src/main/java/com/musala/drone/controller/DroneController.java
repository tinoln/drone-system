package com.musala.drone.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musala.drone.entity.Drone;
import com.musala.drone.entity.Medication;
import com.musala.drone.exceptions.DroneNotFoundException;
import com.musala.drone.service.DroneService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(path="/api/drone")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Drone> registerDrone(@Valid @NotNull @RequestBody Drone drone) {
    	
        Drone regDrone = droneService.registerDrone(drone);
        return new ResponseEntity<>(regDrone, HttpStatus.CREATED);
    }

    @GetMapping(path = "/available", produces = "application/json")
    public ResponseEntity<List<Drone>> getAvailableDrones() {
    	
        List<Drone> listDrones = droneService.getAvailableDrones();
        return new ResponseEntity<>(listDrones, HttpStatus.OK);
    }
    
    @PostMapping(path = "/loading", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Medication> loadingDroneWithMedication(@Valid @NotNull @RequestBody Medication medication) {
    	Drone availDrone = new Drone();
    	Optional<List<Drone>> listDrones = Optional.of(getAvailableDrones().getBody());
    	if (listDrones.get().size() > 0) {
    		availDrone = listDrones.get().get(0);
    		
    	}
    	
    	medication.setDrone(availDrone);
    	
    	Medication loadingMeds = droneService.loadingDroneWithMedication(medication, medication.getDrone().getId());
    	return new ResponseEntity<>(loadingMeds, HttpStatus.CREATED);
    }
    
    @GetMapping(path = "/loaded/{id}", produces = "application/json")
    public ResponseEntity<Drone> getLoadedDrone(@NotNull @PathVariable Long id) {
    	
         Drone loadedDrone = droneService.getDroneLoadedMedication(id);
         return new ResponseEntity<>(loadedDrone, HttpStatus.OK);
    }
    
    @GetMapping(path = "/batteryCheck/{id}", produces = "application/json")
    public ResponseEntity<Drone> getDroneBatteryLevel(@PathVariable Long id) throws Exception {
    
    	Drone droneLevel = droneService.getDroneBatteryLevel(id);
	    if (droneLevel.getId() == null) {
			throw new DroneNotFoundException("Drone Not Found!");
		}
		return new ResponseEntity<Drone>(droneLevel, HttpStatus.OK);
    }
}
