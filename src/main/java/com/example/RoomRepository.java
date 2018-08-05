package com.example;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface RoomRepository  {

	void addRoom(Room room); 
	List<Room> findAllRooms(); 
	void removeRoom(String id);
	void updateRoom(Room room);
	void reserveRoom(Reservation reservation); 
	void removeReservation(String id);
}


