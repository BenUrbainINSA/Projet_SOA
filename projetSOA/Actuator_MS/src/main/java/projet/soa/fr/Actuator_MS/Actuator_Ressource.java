package projet.soa.fr.Actuator_MS;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Actuator")
public class Actuator_Ressource {
	//LISTE DES Actuator
	private static List<Actuator> Actuator = new ArrayList<>();
	
	//GET ALL Actuator
	@GetMapping
    public List<Actuator> getAllSensors() {
        return Actuator;
    }
	
	//GET Actuator BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Actuator> getActuatorId(@PathVariable int id) {
        return Actuator.stream()
                .filter(c -> c.getActuatorId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Actuator> createCommande(@RequestBody Actuator actuator) {
        int nextId = Actuator.size() + 1;
        actuator.setActuatorId(nextId);

        Actuator.add(actuator);
        return ResponseEntity.status(HttpStatus.CREATED).body(actuator);
    }

//    //GET Actuator BY ROOM
//    @GetMapping("/Room/{id}")
//    public ResponseEntity<Actuator> getRoomId(@PathVariable int id) {
//        return Actuator.stream()
//                .filter(c -> c.getRoomId() == id)
//                .findFirst()
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//    }
	
//----------------------------------------------------------------

//	@RestController
//	@RequestMapping("/IndoorSensorRessource")
//	public class Indoor_Sensor_Ressource {
//		
//		
//		
//	    //GET SENSOR BY ROOM
//	    @GetMapping("/Room/{id}")
//	    public ResponseEntity<Indoor_Sensor> getSensorByRoomId(@PathVariable int id) {
//	        return sensors.stream()
//	                .filter(c -> c.getRoomId() == id)
//	                .findFirst()
//	                .map(ResponseEntity::ok)
//	                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//	    }
//	    
//	    //GET SENSOR BY NAME
//	    @GetMapping("/Name/{name}")
//	    public ResponseEntity<Indoor_Sensor> getSensorByNameId(@PathVariable String name) {
//	        return sensors.stream()
//	                .filter(c -> c.getName() == name)
//	                .findFirst()
//	                .map(ResponseEntity::ok)
//	                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//	    }
//	    
//	    //POST NEW SENSOR
//	    @PostMapping(consumes = "application/json", produces = "application/json")
//	    public ResponseEntity<Indoor_Sensor> createCommande(@RequestBody Indoor_Sensor sensor) {
//	        sensors.add(sensor);
//	        return ResponseEntity.status(HttpStatus.CREATED).body(sensor);
//	    }
//	    
//	    //UPDATE SENSOR BY ID
//	    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
//	    public ResponseEntity<Indoor_Sensor> updateCommande(@PathVariable int id, @RequestBody Indoor_Sensor updatedSensor) {
//	        for (int i = 0; i < sensors.size(); i++) {
//	            if (sensors.get(i).getSensorId() == id) {
//
//	                Indoor_Sensor c = sensors.get(i);
//	                c.setSensorId(updatedSensor.getSensorId());
//	                c.setRoomId(updatedSensor.getRoomId());
//	                c.setMeasurement(updatedSensor.getMeasurement());
//	                c.setName(updatedSensor.getName());
//	                c.setEnabled(updatedSensor.getEnabled());
//
//	                return ResponseEntity.ok(c);
//	            }
//	        }
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//	    }
//
//	}
}
