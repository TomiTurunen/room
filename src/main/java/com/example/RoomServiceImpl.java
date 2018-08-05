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
	
	public List<Room> removeRoom(HttpServletRequest request) {
		List<Room> roomList = repository.findAllRooms();
		for(Room room : roomList) {
			System.out.println("Huoneen nimi " + room.getName());
		}
		System.out.println("here I am !!!");
		return repository.findAllRooms();
	}
	
	public void updateRoom(HttpServletRequest request) {
		Room room = new Room();
		room.setName(request.getParameter("name"));
		room.setSize(request.getParameter("size"));
		repository.addRoom(room);
		System.out.println(room.getName());
	}
}
