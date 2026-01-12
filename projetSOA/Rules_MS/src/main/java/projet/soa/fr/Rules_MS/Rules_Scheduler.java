package projet.soa.fr.Rules_MS;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Rules_Scheduler {

    private final Rules rules;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String INDOOR_SENSOR_URL = "http://localhost:8082/IndoorSensorRessource/Room/Max";
    
    public Rules_Scheduler(Rules rules) {
        this.rules = rules;
    }

    // Exécution toutes les 10 secondes
    @Scheduled(fixedRate = 10000)
    public void runRules() {
    	ResponseEntity<Integer> response = restTemplate.getForEntity(INDOOR_SENSOR_URL, Integer.class);
        
    	int maxRoomId = response.getBody();
    	
    	for (int roomId = 1; roomId <= maxRoomId; roomId++) {
            String result = rules.evaluateByRoomId(roomId);
            System.out.println("Rules executed for room "+ roomId + " : " + result); 
        }
    }
}
