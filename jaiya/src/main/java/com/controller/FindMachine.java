package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.modelmapper.ModelMapper;

import com.connect.mongo.Connect;
import com.dto.FindMachineDto;
import com.dto.TimeToGetPillowDto;
import com.dto.UserDataDto;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
@Path("/machine")
public class FindMachine {
	@Path("/searchMachine")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response SearchMachine(FindMachineDto FindMachineDto) {
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("machine");
		
		
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper mapper = new ModelMapper();
		
		
		
		
		FindMachineDto[] value = null;
		FindMachineDto valueFirst = null;
		
		try {
			FindIterable<Document> data = collection.find();
			int size = Iterables.size(data);
		    value = new FindMachineDto[size];
		    int key = 0;
		    for (Document document :data ) {
		    	value[key++] = mapper.map(document, FindMachineDto.class);
		    }
		    message.addProperty("message", true);
		} catch (Exception e) {
			message.addProperty("message", false);
		  
		}finally {
			message.add("datasearch", gson.toJsonTree(value));
			
		}
		return Response.ok(gson.toJson(message),MediaType.APPLICATION_JSON).build();		
		
	}
	@POST
	@Path("/showallmachine")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response showallmachine(FindMachineDto FindMachineDto) {
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("machine");
		
		//import json , modelmapper
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();
		BasicDBObject searchQuery =new BasicDBObject();
		searchQuery.put("name", FindMachineDto.getName());	
		FindMachineDto value = new FindMachineDto();
		try {
			FindIterable<Document> data = collection.find(searchQuery);
			value = Mapper.map(data.first(), FindMachineDto.class);
			message.addProperty("message", true);
			
			
			
		}catch (Exception e) {
			message.addProperty("message", false);
		}finally {
			message.add("data", gson.toJsonTree(value));
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
}
