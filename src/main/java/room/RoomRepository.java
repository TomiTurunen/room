package room;

import java.util.List;

public interface RoomRepository {

	void addRoom(Room room);

	List<Room> findAllRooms();

	boolean removeRoom(String id);

	void updateRoom(Room room);

	boolean reserveRoom(Reservation reservation);

	void removeReservation(String id);

	String findReserver(String id);

	List<Reservation> findReservationsRooms();

	List<Room> findFreeRooms();
}
