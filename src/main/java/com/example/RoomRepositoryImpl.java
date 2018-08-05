package com.example;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
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
				System.out.println("-------------------------------------------------------");
				Room room = new Gson().fromJson(cursor.next().toJson(), Room.class);
				System.out.println("-------------------------------------------------------");
				System.out.println("NIMI " + room.getName());
				roomList.add(room);
				System.out.println("Here I am2");
			}
		} catch (NoSuchElementException e) {
			System.err.println(e);
		} finally {
			cursor.close();
			System.out.println("Here I am3");
		}
		System.out.println("Here I am4");
		System.out.println(roomList.get(0).getName());
		return roomList;

		/*
		 * MongoCollection<Document> rooms = db.getCollection("rooms"); DBCollection
		 * coll = db.getCollection("testCollection"); DBCollection col = db.get
		 * List<Document> roomList = rooms.find List<DBObject> all =
		 * rooms.find().toArray();
		 * 
		 * 
		 * 
		 * Document doc = new Document("name",room.getName()) .append("size",
		 * room.getSize()); rooms.insertOne(doc); System.out.println("Here I am2");
		 * client.close(); System.out.println("Here I am3");
		 * 
		 * 
		 * Document updateQuery = new Document("song", "One Sweet Day");
		 * songs.updateOne(updateQuery, new Document("$set", new Document("artist",
		 * "Mariah Carey ft. Boyz II Men")));
		 * 
		 * /* Finally we run a query which returns all the hits that spent 10 or more
		 * weeks at number 1.
		 */

		/*
		 * Document findQuery = new Document("weeksAtOne", new Document("$gte",10));
		 * Document orderBy = new Document("name", 1);
		 * 
		 * MongoCursor<Document> cursor =
		 * songs.find(findQuery).sort(orderBy).iterator();
		 * 
		 * try { while (cursor.hasNext()) { Document doc = cursor.next();
		 * System.out.println( "In the " + doc.get("decade") + ", " + doc.get("song") +
		 * " by " + doc.get("artist") + " topped the charts for " +
		 * doc.get("weeksAtOne") + " straight weeks." ); } } finally { cursor.close(); }
		 */

	}

}