package room;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Arrays;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCursor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@Repository("RoomRepository")
public class RoomRepositoryImpl implements RoomRepository {
	
	MongoClientURI uri = new MongoClientURI(
			"mongodb://heroku_vkcpnxnn:2emfcpp2i8r4ulv8fd1mdpdlqu@ds255715.mlab.com:55715/heroku_vkcpnxnn");

	@Autowired
	MongoTemplate template;

	public RoomRepositoryImpl() {
	}

	@Override
	public void addRoom(Room room) {
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		System.out.println(db.getCollection("rooms"));
		MongoCollection<Document> rooms = db.getCollection("rooms");
		Document doc = new Document("name", room.getName()).append("size", room.getSize());
		rooms.insertOne(doc);
		client.close();
	}

	@Override
	public List<Room> findAllRooms() {
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> rooms = db.getCollection("rooms");
		List<Room> roomList = new ArrayList<>();
		List<Reservation> reservations = findReservationsRooms();
		System.out.println("SEEEEEEEEEEEEEEEEEEEEEEEE!!!!!!!!!!!!!00");
		System.out.println("SEEEEEEEEEEEEEEEEEEEEEEEE!!!!!!!!!!!!!" + reservations.get(0).getReserverName());
		// List<Document> roomDocuments = rooms.find
		MongoCursor<Document> cursor = rooms.find().iterator();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				Room room = new Gson().fromJson(doc.toJson(), Room.class);
				//ObjectId id = (ObjectId)doc.get( "_id" );
				room.setId(doc.get( "_id" ).toString());
				roomList.add(room);		
				Reservation roomReservation = reservations.stream().filter(reservation -> room.getId().equals(reservation.getRoomId())).findFirst().orElse(null);
				if(roomReservation != null) {
					System.out.println(roomReservation.getReserverName());
					room.setReserverName(roomReservation.getReserverName());
				}
				System.out.println("RESERVER NAME " + room.getReserverName());
			}
		} catch (NoSuchElementException e) {
			System.err.println(e);
		} finally {
			cursor.close();			
		}
		client.close();
		return roomList;
	}
	
	@Override
	public List<Room> findFreeRooms() {
		System.out.println("QQQQQQQQQSEEEEEEEEEEEEEEEEEEEEEEEE!!!!!!!!!!!!!00");
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> rooms = db.getCollection("rooms");
		List<Room> roomList = new ArrayList<>();
		List<Reservation> reservations = findReservationsRooms();
		// List<Document> roomDocuments = rooms.find
		MongoCursor<Document> cursor = rooms.find().iterator();
		try {
			while (cursor.hasNext()) {
				System.out.println("ddd");
				Document doc = cursor.next();
				Room room = new Gson().fromJson(doc.toJson(), Room.class);
				room.setId(doc.get( "_id" ).toString());
				Reservation roomReservation = reservations.stream().filter(reservation -> room.getId().equals(reservation.getRoomId())).findFirst().orElse(null);
				if(roomReservation != null) {
					continue;
				}
				roomList.add(room);	
			}
		} catch (NoSuchElementException e) {
			System.err.println(e);
		} finally {
			cursor.close();			
		}
		client.close();
		System.out.println("wwwQQQQQQQQQSEEEEEEEEEEEEEEEEEEEEEEEE!!!!!!!!!!!!!00");
		return roomList;
	}
	
	@Override
	public List<Reservation> findReservationsRooms() {
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> reservations = db.getCollection("reservations");
		List<Reservation> reservationList = new ArrayList<>();
		MongoCursor<Document> cursor = reservations.find().iterator();		
		try {
			System.out.println("NIIIDA!!!! ");
			while (cursor.hasNext()) {
				System.out.println("NADA!!!!");
				Document doc = cursor.next();
				Reservation reservation = new Gson().fromJson(doc.toJson(), Reservation.class);
				reservation.setId(doc.get( "_id" ).toString());
				reservationList.add(reservation);
			}
		} catch (NoSuchElementException e) {
			System.err.println(e);
		} finally {
			cursor.close();
			System.out.println("Here I am3");
		}
		client.close();
		return reservationList;
	}
	@Override
	public Room findRoom(String id) {
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> rooms = db.getCollection("rooms");
		Bson filter = new Document("_id", new ObjectId(id));
		Document doc = rooms.find(filter).first();
		Room room = new Gson().fromJson(doc.toJson(), Room.class);
		room.setId(doc.get( "_id" ).toString());
		client.close();
		return room;
	}
	
	@Override
	public String findReserver(String id) {
		String reserverName = null;
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> reservations = db.getCollection("reservations");
		Bson filter = new Document("roomId", id);
		Document doc = reservations.find(filter).first();
		if (doc != null ) {
			reserverName = doc.get("reserverName").toString();
		}
		client.close();
		return reserverName;
	}
	
	@Override
	public void removeRoom(String id) {		
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> rooms = db.getCollection("rooms");
		System.out.println(id);
		rooms.deleteOne(new Document("_id", new ObjectId(id)));
		client.close();
		
	}
	
	@Override
	public void updateRoom(Room room) {		
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> rooms = db.getCollection("rooms");
		//Document doc = new Document("name", room.getName()).append("size", room.getSize());
		Bson filter = new Document("_id", new ObjectId(room.getId()));
		Bson newValue = new Document("name", room.getName()).append("size", room.getSize());
		Bson updateOperationDocument = new Document("$set", newValue);		
		rooms.updateOne(filter, updateOperationDocument);
		client.close();
		
	}
	
	@Override
	public void reserveRoom(Reservation reservation) {
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		System.out.println(db.getCollection("rooms"));
		MongoCollection<Document> reservations = db.getCollection("reservations");
		Document doc = new Document("reserverName", reservation.getReserverName()).append("roomId", reservation.getRoomId());
		reservations.insertOne(doc);
		client.close();
	}
	
	@Override
	public void removeReservation(String roomId) {		
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> reservations = db.getCollection("reservations");
		reservations.deleteOne(new Document("roomId", roomId));
		client.close();
		
	}

}