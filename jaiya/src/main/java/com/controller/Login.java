package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.modelmapper.ModelMapper;

import com.connect.mongo.Connect;
import com.dto.LoginDto;
import com.dto.RegisterDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Path("/login")
public class Login {
	
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(LoginDto LoginDto) {
		// import mongodb
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("user");
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();	
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("username", LoginDto.getUsername());
		searchQuery.put("password", LoginDto.getPassword());
		
		LoginDto value = new LoginDto();
		try {
			FindIterable<Document> data = collection.find(searchQuery);
			value = Mapper.map(data.first(), LoginDto.class);
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}finally {
			message.add("data", gson.toJsonTree(value));
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
}
