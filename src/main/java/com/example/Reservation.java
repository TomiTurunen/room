package com.example;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Document(collection = "reservation")
public class Reservation {

	private static final Logger logger = LoggerFactory.getLogger(Room.class);

    @Id
    private String id;

    private String reserverName;
    private String roomId;
    private Room room;  
    
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
    
    public Room getRoom() {
    	return room;
    }
    
    public void setRoom(Room room) {
    	this.room = room;
    }
}
