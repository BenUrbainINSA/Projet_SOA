package projet.soa.fr.Rules_MS;

public class History_Maison {

    private Integer id;
    private int roomId;
    private String action;
    private String reason;
    private String timestamp;

    public History_Maison() {}

    public History_Maison(Integer id, int roomId, String action, String reason, String timestamp) {
        this.id = id;
        this.roomId = roomId;
        this.action = action;
        this.reason = reason;
        this.timestamp = timestamp;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
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
