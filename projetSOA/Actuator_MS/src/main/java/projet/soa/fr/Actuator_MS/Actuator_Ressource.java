package projet.soa.fr.Actuator_MS;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    //GET Actuator BY ROOM
    @GetMapping("/Room/{id}")
    public ResponseEntity<?> getRoomId(@PathVariable int id) {
        
        List<Actuator> result = Actuator.stream()
                .filter(d -> d.getRoomId() == id)
                .toList();

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucun capteur trouvé pour la roomId : " + id);
        }

        return ResponseEntity.ok(result);
    }
    
    //GET Actuator BY NAME
    @GetMapping("/Name/{name}")
    public ResponseEntity<Actuator> getSensorByNameId(@PathVariable String name) {
        return Actuator.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    //POST NEW ACTUATOR 
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Actuator> createCommande(@RequestBody Actuator actuator) {
        int nextId = Actuator.size() + 1; //incrémente automatiquement 
        actuator.setActuatorId(nextId);
        Actuator.add(actuator);
        return ResponseEntity.status(HttpStatus.CREATED).body(actuator);
    }
    
    //UPDATE SENSOR BY ID
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Actuator> updateCommande(@PathVariable int id, @RequestBody Actuator updatedSensor) {
        for (int i = 0; i < Actuator.size(); i++) {
            if (Actuator.get(i).getActuatorId() == id) {

            	Actuator c = Actuator.get(i);
                c.setActuatorId(updatedSensor.getActuatorId());
                c.setRoomId(updatedSensor.getRoomId());
                c.setState(updatedSensor.getState());
                c.setName(updatedSensor.getName());
                c.setEnabled(updatedSensor.getEnabled());

                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    
}
