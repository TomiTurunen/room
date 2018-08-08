package room;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Document(collection = "reservation")
public class Reservation {

    @Id
    private String id;

    private String reserverName;
    private String roomId;
    
    public String getId() {
    	return id;
    }
    
    public void setId(String id) {
    	this.id = id;
    }
    
    public String getReserverName() {
    	return reserverName;
    }
    
    public void setReserverName(String reserverName) {
    	this.reserverName = reserverName;
    }
    
    public String getRoomId() {
    	return roomId;
    }
    
    public void setRoomId(String roomId) {
    	this.roomId = roomId;
    }   
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Reservation){
        	Reservation toCompare = (Reservation) o;
            return this.roomId.equals(toCompare.roomId);
        }
        return false;
    }
    
    
}
