package com.musala.drone.response;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data

public class BatteryLevelResponse {
	
	private String serialNumber;
	private String battery;

}
