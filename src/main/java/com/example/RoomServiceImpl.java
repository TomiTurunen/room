package com.example;

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

}
