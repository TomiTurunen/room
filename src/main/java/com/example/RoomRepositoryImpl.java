package com.example;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class RoomRepositoryImpl implements RoomRepository {
	
	@Autowired
    MongoTemplate template;
	
	public RoomRepositoryImpl() {}
	
    
	@Override
    public void addRoom(Room room) {
		System.out.println("Here I am");
    	//TODO mieti fiksumpi tapa
        MongoClientURI uri  = new MongoClientURI("mongodb://heroku_vkcpnxnn:2emfcpp2i8r4ulv8fd1mdpdlqu@ds255715.mlab.com:55715/heroku_vkcpnxnn"); 
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        //db.getCollection("rooms").insertOne((Document)room);
        System.out.println(db.getCollection("rooms"));
    	MongoCollection<Document> rooms = db.getCollection("rooms");
    	Document doc = new Document("name","001")
    			.append("size", "2");
    	rooms.insertOne(doc);
    	System.out.println("Here I am2");
    	 client.close();   	
    	 System.out.println("Here I am3");
    }

	
}