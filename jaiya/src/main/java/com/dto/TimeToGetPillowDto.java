package com.dto;

import org.bson.types.ObjectId;

public class TimeToGetPillowDto {
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
	String id;
	String alertid;
	public String getAlertid() {
		return alertid;
	}
	public void setAlertid(String alertid) {
		this.alertid = alertid;
	}
	public ObjectId getId() {
		return new ObjectId(id);
	}
	public void setId(String id) {
		this.id = _id;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
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
