package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.checkerframework.checker.units.qual.s;
import org.codehaus.jettison.json.JSONObject;
import org.modelmapper.ModelMapper;

import com.connect.mongo.Connect;
import com.dto.MapLocationDto;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Path("/MapLocation")
public class MapLocation {
	//	insert hospital 
//		@Path("/Hospital")
//		@POST
//		@Consumes(MediaType.APPLICATION_JSON)
//		public Response hospital(MapLocationDto mapLocationDto) {
//			Connect mongo = new Connect();
//			MongoCollection<Document> collection = mongo.db.getCollection("hospital");
//			
//			
//			JsonObject message = new JsonObject();
//			Gson gson = new Gson();
//			ModelMapper mapper = new ModelMapper();
//			
//			
//			BasicDBObject searchQuery = new BasicDBObject();
//			searchQuery.put("name", mapLocationDto.getName());
//			
//			try {
//				FindIterable<Document> data = collection.find(searchQuery);
//				mapLocationDto = mapper.map(data.first(), mapLocationDto.getClass());
//				message.addProperty("message","Duplicate");
//				
//			} catch (Exception e) {
//				mapLocationDto.setStatus(1);
//				String json  = gson.toJson(mapLocationDto);
//				Document document = Document.parse(json);
//				try {
//					collection.insertOne(document);
//					message.addProperty("message", true);
//				} catch (Exception e2) {
//					message.addProperty("message", false);
//				}
//				
//			}
//			return Response.ok(gson.toJson(message),MediaType.APPLICATION_JSON).build();
//			
//		}
	@Path("/SearchHospital")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response hospital(MapLocationDto mapLocationDto) {
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("hospital");
		
		
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper mapper = new ModelMapper();
		
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", mapLocationDto.getName());
		
		MapLocationDto[] value = null;
		MapLocationDto valueFirst = null;
		
		try {
			FindIterable<Document> data = collection.find(searchQuery);
			valueFirst = mapper.map(data.first(), MapLocationDto.class);
			message.addProperty("message", true);			
		} catch (Exception e) {
			FindIterable<Document> data = collection.find();
			int size = Iterables.size(data);
		    value = new MapLocationDto[size];
		    int key = 0;
		    for (Document document :data ) {
		    	value[key++] =  mapper.map(document, MapLocationDto.class);
		    }
		    message.addProperty("message", true);
		}finally {
			message.add("datasearch", gson.toJsonTree(value));
			message.add("data", gson.toJsonTree(valueFirst));
		}
		return Response.ok(gson.toJson(message),MediaType.APPLICATION_JSON).build();		
		
	}
}
