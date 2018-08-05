package com.example;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Inject
	private RoomRepository repository;
	
	public RoomServiceImpl() {}
	
	
	public void addRoom(HttpServletRequest request) {
		Room room = new Room();
		room.setName(request.getParameter("name"));
		room.setSize(request.getParameter("size"));
		repository.addRoom(room);
		System.out.println(room.getName());
	}
	
	public List<Room> findAllRooms() {
		List<Room> roomList = repository.findAllRooms();
		for(Room room : roomList) {
			System.out.println("Huoneen nimi " + room.getName());
		}
		System.out.println("here I am !!!");
		return repository.findAllRooms();
	}
	
	public void removeRoom(HttpServletRequest request) {
		String id = request.getParameter("roomId");
		repository.removeRoom(id);
	}
	
	public void updateRoom(HttpServletRequest request) {
		Room room = new Room();
		room.setId(request.getParameter("updateRoomId"));
		System.out.println("updateRoomId"+request.getParameter("updateRoomId"));
		room.setName(request.getParameter("name"));
		room.setSize(request.getParameter("size"));
		repository.updateRoom(room);
		System.out.println(room.getName());
	}
}
