package com.dao;

import org.bson.types.ObjectId;

public class TimeToGetPillowDao {
	String time;
	String drug;
	int amount;
	String volume;
	String duration;
	int alert;
	int status_alert;
	int status;
	String userId;
	String _id;
	
	public void setId(String id) {
		this._id = _id;
	}
	public String get_id() {
		return _id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDrug() {
		return drug;
	}
	public void setDrug(String drug) {
		this.drug = drug;
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public int getAlert() {
		return alert;
	}
	public void setAlert(int alert) {
		this.alert = alert;
	}
	public int getStatus_alert() {
		return status_alert;
	}
	public void setStatus_alert(int status_alert) {
		this.status_alert = status_alert;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
