package projet.soa.fr.Rules_MS;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import projet.soa.fr.Indoor_Sensor_MS.*;
import projet.soa.fr.Outdoor_Sensor_MS.*;
import projet.soa.fr.Actuator_MS.*;
import projet.soa.fr.History_MS.*;

@Component
public class Rules {
	
    private final String indoorSensorUrl = "http://localhost:8082/IndoorSensorRessource/Room/";
    private final String outdoorSensorUrl = "http://localhost:8081/OutdoorSensorRessource/Room/";
    private final String actuatorUrl = "http://localhost:8084/Actuator/";
    private final String historyUrl = "http://localhost:8083/History/";

 
    private final RestTemplate restTemplate = new RestTemplate();
    
    public String evaluateByRoomId(int roomId) {
    	
    	Indoor_Sensor[] inSensors = restTemplate.getForObject(indoorSensorUrl + roomId, Indoor_Sensor[].class);
    	Indoor_Sensor inSensor = inSensors[0];
    	
    	Outdoor_Sensor[] outSensors = restTemplate.getForObject(outdoorSensorUrl + roomId, Outdoor_Sensor[].class);
    	Outdoor_Sensor outSensor = outSensors[0];
    	
    	Actuator[] actuators = restTemplate.getForObject(actuatorUrl+"Room/"+roomId, Actuator[].class);
    	Actuator actuator = actuators[0];
    	
        if (inSensor == null || outSensor == null) {
            return "Missing sensors for room " + roomId;
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

	    History log = new History(
	    		null,
	            roomId,
	            action,
	            reason,
	            null
	    );
	
	    restTemplate.postForObject(historyUrl, log, History.class);
    }

}
