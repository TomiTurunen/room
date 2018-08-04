
package com.example;

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
	private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger( Main.class.getName() );
	 @Inject
	private RoomService service;
	//RoomService RoomService = new RoomServiceImpl();

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
    
    @RequestMapping("/hello")
    String hello(Map<String, Object> model) {
        RelativisticModel.select();
        String energy = System.getenv().get("ENERGY");
        if (energy == null) {
           energy = "12 GeV";
        }
        Amount<Mass> m = Amount.valueOf(energy).to(KILOGRAM);
        model.put("science", "E=mc^2: " + energy + " = "  + m.toString());
        return "hello";
    }
    
    @RequestMapping("/add")
    String add(Map<String, Object> model) {
    	System.err.println("Hello, logs1!");
        model.put("rooms","Room1<br>Room2");
        return "add";
    }
    
    @RequestMapping("/remove")
    String remove(Map<String, Object> model) {
            model.put("rooms2","Room3<br>Room4");
            return "remove";
    }
    
    @RequestMapping("/main")
    String main(Map<String, Object> model) {
        return "main";
    }
    
    @RequestMapping("/edit")
    String edit(Map<String, Object> model) {    
    	model.put("rooms","301");
         return "edit";
    }
    
    @RequestMapping("/addRoom")
    @ResponseBody String addRoom(HttpServletRequest request) throws Exception { 
    	logger.info("test");
    	System.err.println("Hello, logs4!");
    	System.out.println(request.getParameterMap());
    	for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet())
    	{
    	    System.out.println(entry.getKey() + "/" + entry.getValue());
    	}
    	System.out.println(request.getParameter("name"));
    	System.out.println(request.getParameter("size"));
    	service.addRoom(request);
         return "add";
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
