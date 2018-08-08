package room;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.google.gson.Gson;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository("RoomRepository")
public class RoomRepositoryImpl implements RoomRepository {
	final String MONGO_URI = "mongodb://heroku_vkcpnxnn:2emfcpp2i8r4ulv8fd1mdpdlqu@ds255715.mlab.com:55715/heroku_vkcpnxnn";
	MongoClientURI uri = new MongoClientURI(MONGO_URI);

	public RoomRepositoryImpl() {
	}

	@Override
	public void addRoom(Room room) {
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
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
		MongoCursor<Document> cursor = rooms.find().sort(new BasicDBObject("name", 1)).iterator();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				Room room = new Gson().fromJson(doc.toJson(), Room.class);
				room.setId(doc.get("_id").toString());
				roomList.add(room);
				Reservation roomReservation = reservations.stream()
						.filter(reservation -> room.getId().equals(reservation.getRoomId())).findFirst().orElse(null);
				if (roomReservation != null) {
					room.setReserverName(roomReservation.getReserverName());
				}
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
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> rooms = db.getCollection("rooms");
		List<Room> roomList = new ArrayList<>();
		List<Reservation> reservations = findReservationsRooms();
		MongoCursor<Document> cursor = rooms.find().sort(new BasicDBObject("name", 1)).iterator();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				Room room = new Gson().fromJson(doc.toJson(), Room.class);
				room.setId(doc.get("_id").toString());
				Reservation roomReservation = reservations.stream()
						.filter(reservation -> room.getId().equals(reservation.getRoomId())).findFirst().orElse(null);
				if (roomReservation != null) {
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
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				Reservation reservation = new Gson().fromJson(doc.toJson(), Reservation.class);
				reservation.setId(doc.get("_id").toString());
				reservationList.add(reservation);
			}
		} catch (NoSuchElementException e) {
			System.err.println(e);
		} finally {
			cursor.close();
		}
		client.close();
		return reservationList;
	}

	@Override
	public String findReserver(String id) {
		String reserverName = null;
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> reservations = db.getCollection("reservations");
		Bson filter = new Document("roomId", id);
		Document doc = reservations.find(filter).first();
		if (doc != null) {
			reserverName = doc.get("reserverName").toString();
		}
		client.close();
		return reserverName;
	}

	@Override
	public boolean removeRoom(String id) {
		List<Reservation> reservationList = findReservationsRooms();
		for (Reservation reservation : reservationList) {
			if (reservation.getRoomId().equals(id)) {
				return false;
			}
		}
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> rooms = db.getCollection("rooms");
		rooms.deleteOne(new Document("_id", new ObjectId(id)));
		client.close();
		return true;

	}

	@Override
	public void updateRoom(Room room) {
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> rooms = db.getCollection("rooms");
		Bson filter = new Document("_id", new ObjectId(room.getId()));
		Bson newValue = new Document("name", room.getName()).append("size", room.getSize());
		Bson updateOperationDocument = new Document("$set", newValue);
		rooms.updateOne(filter, updateOperationDocument);
		client.close();
	}

	@Override
	public boolean reserveRoom(Reservation reservation) {
		List<Reservation> reservationList = findReservationsRooms();
		if (reservationList.contains(reservation)) {
			return false;
		}
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> reservations = db.getCollection("reservations");
		Document doc = new Document("reserverName", reservation.getReserverName()).append("roomId",
				reservation.getRoomId());
		reservations.insertOne(doc);
		client.close();
		return true;
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