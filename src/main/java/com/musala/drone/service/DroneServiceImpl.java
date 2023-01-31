package com.musala.drone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musala.drone.entity.Drone;
import com.musala.drone.entity.Medication;
import com.musala.drone.enums.State;
import com.musala.drone.exceptions.DroneNotFoundException;
import com.musala.drone.exceptions.MedicationNotFoundException;
import com.musala.drone.repository.DroneRepository;
import com.musala.drone.repository.MedicationRepository;

@Service
public class DroneServiceImpl implements DroneService {

    @Autowired
    private DroneRepository droneRepository;
    
    @Autowired
    private MedicationRepository medicationReposirory;

    @Override
    public Drone registerDrone(Drone drone) {
        return droneRepository.save(drone);
    }

	@Override
	public Drone getDroneLoadedMedication(Long droneId) {
	  
		Optional<Drone> loadedDrone = Optional.of(droneRepository.findById(droneId))
				.filter(drone -> drone.get().getState().equals(State.LOADED))
				.orElseThrow(() -> new RuntimeException("Drone " + droneId + " not found"));
		  
		Drone drone = new Drone();
		drone.setId(loadedDrone.get().getId());
		drone.setSerialNumber(loadedDrone.get().getSerialNumber());
		drone.setModel(loadedDrone.get().getModel());
		drone.setWeightLimit(loadedDrone.get().getWeightLimit());
		drone.setBattery(loadedDrone.get().getBattery());
		drone.setState(loadedDrone.get().getState()); 
		  
		List<Medication> medications = new ArrayList<>();
		if (loadedDrone.isPresent()){
		   medications =  medicationReposirory.findAll();
		}
		drone.setMedications(medications);
		  
		return drone;
	}
	 

    @Override
    public List<Drone> getAvailableDrones() {
    	
    	List<Drone> availableDrone = Optional.of(droneRepository.findAll())
    			.orElseThrow(() -> new DroneNotFoundException("Drone not found"));
    	
    	return availableDrone.stream()
    			.filter(drone -> drone.getBattery() > 25)
    			.filter(drone -> drone.getState().equals(State.IDLE))
    			.toList();
         
    }
    
    @Override
    public Medication loadingDroneWithMedication(Medication medication, Long droneId){
    	
    	Optional<Drone> medDrone = Optional.of(droneRepository.findById(droneId))
    			.orElseThrow(() -> new DroneNotFoundException("Drone not found"));

        Optional<Medication> medicationObject = Optional.of(medicationReposirory.findById(droneId))
        		.orElseThrow(()-> new MedicationNotFoundException("Medication not found"));
    	
        List<Medication> medications = getDroneLoadedMedication(droneId).getMedications();
        List<Double> weights = new ArrayList<>();
        for (Medication md: medications) {
            weights.add(md.getWeight());
        }
        //double addedWeight = weights.stream().mapToDouble(Double::doubleValue).sum();
        double totalWeight = weights.stream().reduce((double) 0, Double::sum);
        if (medDrone.isPresent()){
            medication.setDrone(medDrone.orElse(null));
            if (totalWeight < 500 || medication.getWeight() < (500 - totalWeight)){
                medicationObject =  Optional.of(medicationReposirory.save(medication));
                if (medDrone.get().getState().equals(State.IDLE)) {
                	updateLoadingDrone(medDrone.get(), medDrone.get().getId(), State.LOADING);
                }
            }
        }
        return medicationObject.orElse(medication);
    }
    
	public Drone getDroneBatteryLevel(Long id) throws Exception {
	    	
	        Optional<Drone> optionalCheck = Optional.of(droneRepository.findById(id))
	        		.orElseThrow(() -> new DroneNotFoundException("Drone id not found: " + id));
	        
	        Drone batteryCheck = new Drone();
	        	batteryCheck.setSerialNumber(optionalCheck.get().getSerialNumber());
	        	batteryCheck.setBattery(optionalCheck.get().getBattery());
	        	
	        return batteryCheck;
	 }
	
	public Drone updateLoadingDrone(Drone drone, Long droneId, State state) {
	    drone.setId(droneId);
	    drone.setState(state);
	    return droneRepository.save(drone);
	}
}
