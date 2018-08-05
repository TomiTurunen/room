package com.example;

import java.util.List;

public interface RoomRepository  {

	void addRoom(Room room); 
	List<Room> findAllRooms(); 
}


