package projet.soa.fr.Outdoor_Sensor_MS;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/IndoorSensorRessource")
public class Outdoor_Sensor_Ressource {
	
	//LISTE DES INDOOR SENSORS
	private static List<Outdoor_Sensor> sensors = new ArrayList<>();
	
	//GET ALL SENSORS
	@GetMapping
    public List<Outdoor_Sensor> getAllSensors() {
        return sensors;
    }
    
    //GET SENSOR BY ID
    @GetMapping("/Sensor/{id}")
    public ResponseEntity<Outdoor_Sensor> getSensorBySensorId(@PathVariable int id) {
        return sensors.stream()
                .filter(c -> c.getSensorId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
	
    //GET SENSOR BY ROOM
    @GetMapping("/Room/{id}")
    public ResponseEntity<?> getSensorByRoomId(@PathVariable int id) {
        
        List<Outdoor_Sensor> result = sensors.stream()
                .filter(d -> d.getNearRoomId() == id)
                .toList();

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucun capteur trouvé pour la roomId : " + id);
        }

        return ResponseEntity.ok(result);
    }
    
    //GET SENSOR BY NAME
    @GetMapping("/Name/{name}")
    public ResponseEntity<Outdoor_Sensor> getSensorByNameId(@PathVariable String name) {
        return sensors.stream()
        		.filter(c -> c.getName().equals(name))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    //GET TEMPERATURE BY ROOM ID
    @GetMapping("/Room/{id}/Temperature")
    public ResponseEntity<String> getTemperatureByRoomId(@PathVariable int id) {
        return sensors.stream()
                .filter(c -> c.getNearRoomId() == id)
                .findFirst()
                .map(sensor -> ResponseEntity.ok(sensor.getMeasurement() + "°C"))
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Aucun capteur trouvé pour la salle " + id));
    }

    
    //POST NEW SENSOR
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Outdoor_Sensor> createCommande(@RequestBody Outdoor_Sensor sensor) {
        int nextId = sensors.size() + 1;
        sensor.setSensorId(nextId);

        sensors.add(sensor);
        return ResponseEntity.status(HttpStatus.CREATED).body(sensor);
    }
    
    //UPDATE SENSOR BY ID
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Outdoor_Sensor> updateCommande(@PathVariable int id, @RequestBody Outdoor_Sensor updatedSensor) {
        for (int i = 0; i < sensors.size(); i++) {
            if (sensors.get(i).getSensorId() == id) {

                Outdoor_Sensor c = sensors.get(i);
                c.setSensorId(updatedSensor.getSensorId());
                c.setNearRoomId(updatedSensor.getNearRoomId());
                c.setMeasurement(updatedSensor.getMeasurement());
                c.setName(updatedSensor.getName());
                c.setEnabled(updatedSensor.getEnabled());

                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    //DELETE SENSOR BY ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSensor(@PathVariable int id) {
        boolean removed = sensors.removeIf(c -> c.getSensorId() == id);

        if (removed) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Suppression réussie pour le capteur avec l'id " + id);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Échec de la suppression : aucun capteur trouvé avec l'id " + id);
        }
    }

}
