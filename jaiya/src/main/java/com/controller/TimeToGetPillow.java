package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;

import com.connect.mongo.Connect;
import com.dao.TimeToGetPillowDao;
import com.dto.Test;
import com.dto.TimeToGetPillowDto;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

@Path("/timetogetpillow")
public class TimeToGetPillow {
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(TimeToGetPillowDto TimeToGetPillowDto) {
		// import mongodb
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("timetogetpillow");
		
		//import json , modelmapper
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("time", TimeToGetPillowDto.getTime());
		searchQuery.put("status",3);
		
		TimeToGetPillowDto timeToGetPillowDto = new TimeToGetPillowDto();
		
		// if find return false else register user
		try {
			FindIterable<Document> data = collection.find(searchQuery);
			timeToGetPillowDto = Mapper.map(data.first(),TimeToGetPillowDto.getClass());
			message.addProperty("message", false);
			
		} catch (Exception e) {
//			message.addProperty("message", true);
			TimeToGetPillowDto.setStatus_alert(1);
			TimeToGetPillowDto.setStatus(1);
			String json = gson.toJson(TimeToGetPillowDto);
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
	
	@POST
	@Path("/setAlertAgain")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(TimeToGetPillowDto TimeToGetPillowDto) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("timetogetpillow");
		
		TimeToGetPillowDao timeToGetPillowDao = new TimeToGetPillowDao();
//
		
//		ReisterDao.setId(RegisterDto.getId());
		timeToGetPillowDao.setStatus_alert(2);
		
		
		
		String json = gson.toJson(timeToGetPillowDao);
		Document document = Document.parse(json);
		
		BasicDBObject setQuery = new BasicDBObject();
        setQuery.put("$set", document);
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", timeToGetPillowDao.get_id());
		
		try {
			collection.updateOne(searchQuery, setQuery);
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	@POST
//	@Path("/delete?id={id}")
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
//	public Response delete(@PathParam("id") String id) {
	public Response delete(Test test) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("timetogetpillow");
		
		
		try {
			collection.deleteOne(Filters.eq("_id", new ObjectId(test.getId()))); 
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/changeAlert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeAlert(TimeToGetPillowDto TimeToGetPillowDto) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("timetogetpillow");
		
		TimeToGetPillowDao timeToGetPillowDao = new TimeToGetPillowDao();
//
		
//		ReisterDao.setId(RegisterDto.getId());
		timeToGetPillowDao.setStatus(3);
		
		
		
		String json = gson.toJson(timeToGetPillowDao);
		Document document = Document.parse(json);
		
		BasicDBObject setQuery = new BasicDBObject();
        setQuery.put("$set", document);
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", timeToGetPillowDao.get_id());
		
		try {
			collection.updateOne(searchQuery, setQuery);
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/history")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response history(TimeToGetPillowDto TimeToGetPillowDto) {
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("timetogetpillow");
		
		//import json , modelmapper
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();	
		BasicDBObject searchQuery =new BasicDBObject();
		searchQuery.put("userId", TimeToGetPillowDto.getUserId());
		searchQuery.put("status",2);	
		TimeToGetPillowDto[] value = null;
		try {
			FindIterable<Document> data = collection.find(searchQuery);
			int size = Iterables.size(data);
			value = new TimeToGetPillowDto[size];
			int key = 0;
			for (Document document : data) {
				value[key++] = Mapper.map(document, TimeToGetPillowDto.class);
			}
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}finally {
			message.add("data", gson.toJsonTree(value));
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	@POST
	@Path("/showalltimtogetpillow")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response showalltimtogetpillow(TimeToGetPillowDto TimeToGetPillowDto) {
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("timetogetpillow");
		
		//import json , modelmapper
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();
		BasicDBObject searchQuery =new BasicDBObject();
		searchQuery.put("userId", TimeToGetPillowDto.getUserId());	
		searchQuery.put("status",1);
		TimeToGetPillowDto[] value = null;
		try {
			FindIterable<Document> data = collection.find(searchQuery);
			int size = Iterables.size(data);
			value = new TimeToGetPillowDto[size];
			int key = 0;
			for (Document document : data) {
				value[key++] = Mapper.map(document, TimeToGetPillowDto.class);
			}
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}finally {
			message.add("data", gson.toJsonTree(value));
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	@POST
	@Path("/changestatus")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changestatus(TimeToGetPillowDto TimeToGetPillowDto) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("timetogetpillow");
		
		TimeToGetPillowDao timeToGetPillowDao = new TimeToGetPillowDao();

		timeToGetPillowDao.setStatus(TimeToGetPillowDto.getStatus());
		
		
		String json = gson.toJson(timeToGetPillowDao);
		Document document = Document.parse(json);
		
		BasicDBObject setQuery = new BasicDBObject();
        setQuery.put("$set", document);
        
        System.out.println(document);
       

		BasicDBObject searchQuery = new BasicDBObject();
		
		searchQuery.put("userId", TimeToGetPillowDto.getUserId());
		System.out.println(TimeToGetPillowDto.getUserId());
		
		
		
		String _id = TimeToGetPillowDto.get_id();
//		System.out.println(timeToGetPillowDao.get_id());
		ObjectId id = new ObjectId(_id);
		searchQuery.put("_id", id);
//		System.out.println(id);
		try {
			collection.updateOne(searchQuery, setQuery);
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
}
