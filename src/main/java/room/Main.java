
package room;

//import com.example.repo.roomRepo;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.*;
import javax.inject.Inject;

import static javax.measure.unit.SI.KILOGRAM;
import javax.measure.quantity.Mass;
import javax.servlet.http.HttpServletRequest;

import org.jscience.physics.model.RelativisticModel;
import org.jscience.physics.amount.Amount;

@Controller
@SpringBootApplication
public class Main {
	private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Main.class.getName());
	@Inject
	private RoomService service;

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);
	}

	@RequestMapping("/")
	String index() {
		return "index";
	}

	@RequestMapping("/db")
	String db(Map<String, Object> model) {
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
			stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
			ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

			ArrayList<String> output = new ArrayList<String>();
			while (rs.next()) {
				output.add("Read from DB: " + rs.getTimestamp("tick"));
			}

			model.put("records", output);
			return "db";
		} catch (Exception e) {
			model.put("message", e.getMessage());
			return "error";
		}
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
		logger.info("test");
		System.err.println("Hello, logs4!");
		System.out.println(request.getParameterMap());
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			System.out.println(entry.getKey() + "/" + entry.getValue());
		}
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("size"));
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
		System.out.println("ID!!:" + request.getParameter("roomId"));
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
		System.out.println("ID!!:" + request.getParameter("roomId"));
		// Toteuta osa, etta näkee selkeästi kuka varannut
		model.put("notification", "Room reserve removed successfully!");
		List<Room> rooms = service.findAllRooms();
		model.put("rooms", rooms);
		return "configure";
	}

	@Bean
	public DataSource dataSource() throws SQLException {
		if (dbUrl == null || dbUrl.isEmpty()) {
			return new HikariDataSource();
		} else {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(dbUrl);
			return new HikariDataSource(config);
		}
	}

}
