package room;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
@SpringBootApplication
public class Main {
	@Inject
	private RoomService service;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);
	}

	@RequestMapping("/")
	String index() {
		return "index";
	}

	@RequestMapping("/add")
	String add(Map<String, Object> model) {
		return "add";
	}

	@RequestMapping("/configure")
	String configure(Map<String, Object> model) {
		List<Room> rooms = service.findAllRooms();
		model.put("rooms", rooms);
		return "configure";
	}

	@RequestMapping("/addReserve")
	String edit(Map<String, Object> model) {
		List<Room> rooms = service.findFreeRooms();
		model.put("rooms", rooms);
		return "addReserve";
	}

	@RequestMapping("/showReservations")
	String showReservation(Map<String, Object> model) {
		List<Room> rooms = service.findAllRooms();
		model.put("rooms", rooms);
		return "showReservations";
	}

	@RequestMapping("/addRoom")
	String addRoom(HttpServletRequest request, Map<String, Object> model) throws Exception {
		service.addRoom(request);
		model.put("rooms", "Room added successfully!");
		return "add";
	}

	@RequestMapping("/removeRoom")
	String removeRoom(HttpServletRequest request, Map<String, Object> model) throws Exception {
		// Try reserve room
		if (service.removeRoom(request)) {
			model.put("notification", "Room deleted successfully!");
		} else {
			// Room is reserved while user was configure rooms view
			model.put("notification", "Sorry, cant remove reserved room!");
		}
		List<Room> rooms = service.findAllRooms();
		model.put("rooms", rooms);
		return "configure";
	}

	@RequestMapping("/updateRoom")
	String updateRoom(HttpServletRequest request, Map<String, Object> model) throws Exception {
		service.updateRoom(request);
		model.put("notification", "Room information updated successfully!");
		List<Room> rooms = service.findAllRooms();
		model.put("rooms", rooms);
		return "configure";
	}

	@RequestMapping("/reserveRoom")
	String reserveRoom(HttpServletRequest request, Map<String, Object> model) throws Exception {
		// Try reserve room
		if (service.reserveRoom(request)) {
			model.put("notification", "Room reserved successfully!");
		} else {
			// Room reserve failed if two person try reserve room at same time
			model.put("notification", "Sorry, room is is already reserved!");
		}

		List<Room> rooms = service.findFreeRooms();
		model.put("rooms", rooms);
		return "addReserve";
	}

	@RequestMapping("/removeReserve")
	String removeReserve(HttpServletRequest request, Map<String, Object> model) {
		service.removeReservation(request);
		model.put("notification", "Room reserve removed successfully!");
		List<Room> rooms = service.findAllRooms();
		model.put("rooms", rooms);
		return "configure";
	}

}
