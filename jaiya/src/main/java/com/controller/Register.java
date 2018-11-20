package com.controller;

import javax.print.attribute.standard.Fidelity;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.codecs.Decoder;
import org.modelmapper.ModelMapper;

import com.connect.mongo.Connect;
import com.dao.RegisterDao;
import com.dto.RegisterDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Path("/Register")
public class Register {

	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(RegisterDto RegisterDto) {
		// import mongodb
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("user");
		
		//import json , modelmapper
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();
		
		BasicDBObject searchQuery =new BasicDBObject();
		searchQuery.put("username", RegisterDto.getUsername());
		
		RegisterDto register = new RegisterDto();
		
		// if find return false else register user
		try {
			FindIterable<Document> data = collection.find(searchQuery);
			register = Mapper.map(data.first(),RegisterDto.getClass());
			message.addProperty("message", false);
			
		} catch (Exception e) {
//			message.addProperty("message", true);
			RegisterDto.setStatus(2);
			String json = gson.toJson(RegisterDto);
			Document document = Document.parse(json);
			
			try {
				collection.insertOne(document);
				message.addProperty("message", true);
				
			} catch (Exception error) {
				message.addProperty("message", false);
			}
			return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
			
		}
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
}
