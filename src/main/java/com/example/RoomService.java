package com.example;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface RoomService {

	void addRoom(HttpServletRequest request); 
	List<Room> findAllRooms();
	
}
