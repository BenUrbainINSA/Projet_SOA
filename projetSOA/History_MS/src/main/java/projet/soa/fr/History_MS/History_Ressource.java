package projet.soa.fr.History_MS;


import java.time.Instant;
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
@RequestMapping("/History")
public class History_Ressource {

    private final HistoryRepository historyRepository;

    public History_Ressource(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    // GET ALL LOGS
    @GetMapping
    public List<History> getAllLogs() {
        return historyRepository.findAll();
    }

    // GET LOG BY ID
    @GetMapping("/{id}")
    public ResponseEntity<History> getLogById(@PathVariable int id) {
        return historyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // GET LOGS BY ROOM ID
    @GetMapping("/Room/{roomId}")
    public ResponseEntity<?> getLogsByRoomId(@PathVariable int roomId) {

        List<History> result = historyRepository.findByRoomId(roomId);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucun log trouvé pour la roomId : " + roomId);
        }

        return ResponseEntity.ok(result);
    }

    // POST NEW LOG -> stocké en BDD
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<History> createLog(@RequestBody History log) {

        // on ignore un éventuel logId venant du client, c’est la BDD qui gère
        log.setLogId(null);

        // si timestamp vide on met l'heure actuelle
        if (log.getTimestamp() == null || log.getTimestamp().isEmpty()) {
            log.setTimestamp(Instant.now().toString());
        }

        History saved = historyRepository.save(log);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // UPDATE LOG BY ID
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<History> updateLog(@PathVariable int id, @RequestBody History updatedLog) {
        return historyRepository.findById(id)
                .map(existing -> {
                    existing.setRoomId(updatedLog.getRoomId());
                    existing.setAction(updatedLog.getAction());
                    existing.setReason(updatedLog.getReason());
                    existing.setTimestamp(updatedLog.getTimestamp());
                    History saved = historyRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // DELETE LOG BY ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable int id) {
        if (!historyRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        historyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

