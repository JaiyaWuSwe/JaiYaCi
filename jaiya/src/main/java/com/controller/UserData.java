package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.modelmapper.ModelMapper;

import com.connect.mongo.Connect;
import com.dto.RegisterDto;
import com.dto.UserDataDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Path("/UserData")
public class UserData {
	
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(UserDataDto UserDataDto) {
		// import mongodb
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("userData");
		
		//import json , modelmapper
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();
		
		UserDataDto register = new UserDataDto();
		try {
			String json = gson.toJson(UserDataDto);
			Document document = Document.parse(json);
			collection.insertOne(document);
			message.addProperty("message", true);
			
		} catch (Exception e) {
			message.addProperty("message", false);
		}
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/findOne")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findone(UserDataDto userDataDto) {
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("userData");
		
		//import json , modelmapper
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("userId", userDataDto.getuserId());
		
		UserDataDto value = new UserDataDto();
		try {
			FindIterable<Document> data = collection.find(searchQuery);
			value = Mapper.map(data.first(), UserDataDto.class);
			message.addProperty("message", true);
		}catch (Exception e) {
//			message.addProperty("message", false);
		}finally {
			message.add("data", gson.toJsonTree(value));
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
}


