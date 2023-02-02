package com.musala.drone.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.musala.drone.entity.Drone;
import com.musala.drone.entity.Medication;

@Component
public interface DroneService {

    Drone registerDrone(Drone drone);

    Drone getDroneLoadedMedication(Long id);

    List<Drone> getAvailableDrones();

    Medication loadingDroneWithMedication(Medication medication, Long droneId);
    
    Double getDroneBatteryLevel(Long id) throws Exception;

}
