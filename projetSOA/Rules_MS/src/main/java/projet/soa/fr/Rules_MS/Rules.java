package projet.soa.fr.Rules_MS;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import projet.soa.fr.Indoor_Sensor_MS.*;
import projet.soa.fr.Outdoor_Sensor_MS.*;
import projet.soa.fr.Actuator_MS.*;

@Component
public class Rules {
	
    private final String indoorSensorUrl = "http://localhost:8082/IndoorSensorRessource/Room/";
    private final String outdoorSensorUrl = "http://localhost:8081/OutdoorSensorRessource/Room/";
    private final String actuatorUrl = "http://localhost:8084/Actuator/";
    private final String historyUrl = "http://localhost:8083/History";

 
    private final RestTemplate restTemplate = new RestTemplate();
    
    public String evaluateByRoomId(int roomId) {
    	
        Indoor_Sensor inSensor;
        Outdoor_Sensor outSensor;
        Actuator actuator;
        
        //INDOOR
    	try {
            Indoor_Sensor[] inSensors = restTemplate.getForObject(indoorSensorUrl + roomId, Indoor_Sensor[].class);

            if (inSensors == null || inSensors.length == 0) {
                return "Capteur indoor " + roomId + " n'existe pas";
            }

            inSensor = inSensors[0];

        } catch (HttpClientErrorException.NotFound e) {
            return "Capteur indoor " + roomId + " n'existe pas";
        }
    	
    	//OUTDOOR
        try {
            Outdoor_Sensor[] outSensors = restTemplate.getForObject(outdoorSensorUrl + roomId, Outdoor_Sensor[].class);

            if (outSensors == null || outSensors.length == 0) {
                return "Capteur outdoor " + roomId + " n'existe pas";
            }

            outSensor = outSensors[0];

        } catch (HttpClientErrorException.NotFound e) {
            return "Capteur outdoor " + roomId + " n'existe pas";
        }
        
        //ACTUATOR
        try {
            Actuator[] actuators =
                    restTemplate.getForObject(actuatorUrl + "Room/" + roomId, Actuator[].class);

            if (actuators == null || actuators.length == 0) {
                return "Actuator pour la room " + roomId + " n'existe pas";
            }

            actuator = actuators[0];

        } catch (HttpClientErrorException.NotFound e) {
            return "Actuator pour la room " + roomId + " n'existe pas";
        }
        
        int indoorTemp = inSensor.getMeasurement();
        int outdoorTemp = outSensor.getMeasurement();
        
        if (Math.abs(indoorTemp - outdoorTemp) > 10) {
        	//IL FAIT FROIIIID ou CHAUUUUD
        	
        	if(actuator.getState() == true) {
        		//SI ACTUATOR OUVERT
        		actuator.setState(false); //on ferme la fenetre
        		restTemplate.put(actuatorUrl + actuator.getActuatorId(), actuator);
        		sendLog(roomId, "CLOSE_WINDOW", "Outdoor temp is far compared to indoor (" + outdoorTemp + "°C), Indoor (" + indoorTemp + "°C)");
                System.out.println( +roomId+ "CLOSE_WINDOW"+ "Outdoor temp is far compared to indoor (" + outdoorTemp + "°C) Indoor (" + indoorTemp + "°C)");
        		return "Window closed for room " + roomId;
        	}
        }else {
        	//IL FAIT OKKKKK
        	
        	if(actuator.getState() == false) {
        		//SI ACTUATOR FERME
        		actuator.setState(true); //on ouvre la fenetre
        		restTemplate.put(actuatorUrl + actuator.getActuatorId(), actuator);
        		System.out.println( + actuator.getRoomId() +  "OPEN_WINDOW"+ "Outdoor temp is ok compared to indoor (" + outdoorTemp + "°C) Indoor (" + indoorTemp + "°C)");
        		sendLog(roomId, "OPEN_WINDOW", "Outdoor temp is ok compared to indoor (" + outdoorTemp + "°C), Indoor (" + indoorTemp + "°C)");
        		return "Window opened for room " + roomId;
        	}
        }
        
        return "No action required for room " + roomId;
    }
    
    public void sendLog(int roomId, String action, String reason) {

	    History_Maison log = new History_Maison(
	            null,
	            roomId,
	            action,
	            reason,
	            null
	        );

	        restTemplate.postForObject(historyUrl, log, Void.class);
    }

}
