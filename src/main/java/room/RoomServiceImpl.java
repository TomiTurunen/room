package room;

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
		return repository.findAllRooms();
	}
	
	public List<Room> findFreeRooms() {
		return repository.findFreeRooms();
	}
	
	public boolean removeRoom(HttpServletRequest request) {
		String id = request.getParameter("roomId");
		return repository.removeRoom(id);
	}
	
	public void updateRoom(HttpServletRequest request) {
		Room room = new Room();
		room.setId(request.getParameter("updateRoomId"));
		System.out.println("updateRoomId"+request.getParameter("updateRoomId"));
		room.setName(request.getParameter("name"));
		room.setSize(request.getParameter("size"));
		repository.updateRoom(room);		
	}
	public boolean reserveRoom(HttpServletRequest request) {
		Reservation reservation = new Reservation();
		reservation.setReserverName(request.getParameter("reserverName"));
		reservation.setRoomId(request.getParameter("updateRoomId"));
		return repository.reserveRoom(reservation);
	}
	public void removeReservation(HttpServletRequest request) {
		String id = request.getParameter("roomId");
		repository.removeReservation(id);
	}
}
