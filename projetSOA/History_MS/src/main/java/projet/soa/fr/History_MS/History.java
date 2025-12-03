package projet.soa.fr.History_MS;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "history_logs")   // nom de table 
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logId;    

    private Integer roomId;     
    private String action;      
    private String reason;      
    private String timestamp;   // String ou LocalDateTime 

    public History() {}

    public History(Integer logId, Integer roomId, String action, String reason, String timestamp) {
        this.logId = logId;
        this.roomId = roomId;
        this.action = action;
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
