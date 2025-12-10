package projet.soa.fr.Actuator_MS;


public class Actuator {
	private Integer actuatorId;   
	private Integer roomId;
	private Boolean state;  // open or closed 
    private String name;     
    private Boolean enabled;      // Actvie or not 
    
    public Actuator(){}
    
    public Actuator(Integer actuatorId, Integer roomId, Boolean state, String name, Boolean enabled) {
    	this.actuatorId=actuatorId;
        this.roomId = roomId;
        this.state = state;
        this.name = name;
        this.enabled = enabled;
    }
    
    public Integer getActuatorId() {
        return actuatorId;
    }

    public void setActuatorId(Integer actuatorId) {
        this.actuatorId = actuatorId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    

}
