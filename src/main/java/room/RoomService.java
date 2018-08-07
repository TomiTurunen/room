package room;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface RoomService {

	void addRoom(HttpServletRequest request); 
	List<Room> findAllRooms();
	List<Room> findFreeRooms();
	void removeRoom(HttpServletRequest request);
	void updateRoom(HttpServletRequest request); 
	void reserveRoom(HttpServletRequest request);
	void removeReservation(HttpServletRequest request); 
	
	
}
