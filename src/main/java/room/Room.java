package room;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Document(collection = "rooms")
public class Room {

    @Id
    private String id;

    private String name;
    private String size; 
    private String reserverName; 
    
    public String getId() {
    	return id;
    }
    
    public void setId(String id) {
    	this.id = id;
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getSize() {
    	return size;
    }
    
    public void setSize(String size) {
    	this.size = size;
    }

	public String getReserverName() {
		return reserverName;
	}

	public void setReserverName(String reserverName) {
		this.reserverName = reserverName;
	}
}
