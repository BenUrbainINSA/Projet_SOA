package projet.soa.fr.Rules_MS;

import org.springframework.web.client.RestTemplate;
import projet.soa.fr.Indoor_Sensor_MS.*;
import projet.soa.fr.Outdoor_Sensor_MS.*;
import projet.soa.fr.Actuator_MS.*;
import projet.soa.fr.History_MS.*;

public class Rules {
	
    private final String indoorSensorUrl = "http://localhost:8082/IndoorSensorRessource/Room/";
    private final String outdoorSensorUrl = "http://localhost:8081/OutdoorSensorRessource/Room/";
    private final String actuatorUrl = "http://localhost:8084/Actuator/";
    private final String historyUrl = "http://localhost:8083/History/";
    
    public String evaluateByRoomId(int roomId) {
    	
    	RestTemplate restTemplate = new RestTemplate();
    	
    	Indoor_Sensor inSensor = restTemplate.getForObject(indoorSensorUrl+roomId, Indoor_Sensor.class);
    	Outdoor_Sensor outSensor = restTemplate.getForObject(outdoorSensorUrl+roomId, Outdoor_Sensor.class);
    	Actuator actuator = restTemplate.getForObject(actuatorUrl+"room/"+roomId, Actuator.class);
    	
        if (inSensor == null || outSensor == null) {
            return "Missing sensors for room " + roomId;
        }
        
        int indoorTemp = inSensor.getMeasurement();
        int outdoorTemp = outSensor.getMeasurement();
        
        if (Math.abs(indoorTemp - outdoorTemp) > 10) {
        	//IL FAIT FROIIIID ou CHAUUUUD
        	
        	if(actuator.state == true) {
        		//SI ACTUATOR OUVERT
        		actuator.setState(false); //on ferme la fenetre
        		restTemplate.put(actuatorUrl + actuator.getActuatorId(), actuator);
        		sendLog(roomId, "OPEN_WINDOW", "Outdoor is cold compared to indoor (" + outdoorTemp + "°C) < Indoor (" + indoorTemp + "°C)");
        		return "Window closed for room " + roomId;
        	}
        }else {
        	//IL FAIT OKKKKK
        	
        	if(actuator.state == false) {
        		//SI ACTUATOR FERME
        		actuator.setState(true); //on ouvre la fenetre
        		restTemplate.put(actuatorUrl + actuator.getActuatorId(), actuator);
        		sendLog(roomId, "CLOSE_WINDOW", "Outdoor is hot compared to indoor (" + outdoorTemp + "°C) > Indoor (" + indoorTemp + "°C)");
        		return "Window opened for room " + roomId;
        	}
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
	

}
