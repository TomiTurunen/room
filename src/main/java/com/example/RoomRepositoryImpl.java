package com.example;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
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

	@Autowired
	MongoTemplate template;

	public RoomRepositoryImpl() {
	}

	@Override
	public void addRoom(Room room) {
		System.out.println("Here I am");
		// TODO mieti fiksumpi tapa
		MongoClientURI uri = new MongoClientURI(
				"mongodb://heroku_vkcpnxnn:2emfcpp2i8r4ulv8fd1mdpdlqu@ds255715.mlab.com:55715/heroku_vkcpnxnn");
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		/*
		 * Gson gson = new Gson(); String json = gson.toJson(room); BasicDBObject
		 * basicDBObject = new BasicDBObject("Name", json ); MongoCollection
		 * dbCollection = db.getCollection("rooms");
		 * dbCollection.insertOne(basicDBObject);
		 * 
		 * DBObject dbObject = (DBObject)JSON.parse(json);
		 * 
		 * dbCollection.insertOne(dbObject);
		 */

		// db.getCollection("rooms").insertOne((Document)room);
		// DBCollection employeeCollection = (DBCollection) db.getCollection("rooms");
		// employeeCollection.save((DBObject) room);
		System.out.println(db.getCollection("rooms"));
		MongoCollection<Document> rooms = db.getCollection("rooms");
		Document doc = new Document("name", room.getName()).append("size", room.getSize());
		rooms.insertOne(doc);
		System.out.println("Here I am2");
		client.close();
		System.out.println("Here I am3");
	}

	@Override
	public List<Room> findAllRooms() {
		System.out.println("Here I am");
		// TODO mieti fiksumpi tapa
		MongoClientURI uri = new MongoClientURI(
				"mongodb://heroku_vkcpnxnn:2emfcpp2i8r4ulv8fd1mdpdlqu@ds255715.mlab.com:55715/heroku_vkcpnxnn");
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> rooms = db.getCollection("rooms");
		List<Room> roomList = new ArrayList<>();
		// List<Document> roomDocuments = rooms.find
		MongoCursor<Document> cursor = rooms.find().iterator();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				//String json = cursor.next().toJson();
				//System.out.println(json);
				Room room = new Gson().fromJson(doc.toJson(), Room.class);
				//ObjectId id = (ObjectId)doc.get( "_id" );
				room.setId(doc.get( "_id" ).toString());
				System.out.println("ID " + doc.get("id"));
				System.out.println("name " + doc.get("name"));
				System.out.println("ID " + doc.get("oid"));
				System.out.println((ObjectId)doc.get( "_id" ));
				System.out.println("ID " + room.getId());
				System.out.println("Name " + room.getName());
				System.out.println("Size " + room.getSize());
				roomList.add(room);				
			}
		} catch (NoSuchElementException e) {
			System.err.println(e);
		} finally {
			cursor.close();
			System.out.println("Here I am3");
		}
		System.out.println("Here I am4");
		return roomList;
	}
	
	@Override
	public void removeRoom(String id) {		
		MongoClientURI uri = new MongoClientURI(
				"mongodb://heroku_vkcpnxnn:2emfcpp2i8r4ulv8fd1mdpdlqu@ds255715.mlab.com:55715/heroku_vkcpnxnn");
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> rooms = db.getCollection("rooms");
		System.out.println(id);
		rooms.deleteOne(new Document("_id", new ObjectId(id)));
		client.close();
		
	}
	
	@Override
	public void updateRoom(Room room) {		
		MongoClientURI uri = new MongoClientURI(
				"mongodb://heroku_vkcpnxnn:2emfcpp2i8r4ulv8fd1mdpdlqu@ds255715.mlab.com:55715/heroku_vkcpnxnn");
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> rooms = db.getCollection("rooms");		
		//TODO updating
		
		
		client.close();
		
	}

}