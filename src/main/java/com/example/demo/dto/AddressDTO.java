package com.example.demo.dto;

import java.time.LocalDateTime;

public class AddressDTO {
	private long id;
	private String line1;
	private String line2;
	private String city;
	private String state;
	private String pincode;
	private LocalDateTime dateCreated;
	private long userId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "AddressDTO [id=" + id + ", line1=" + line1 + ", line2=" + line2 + ", city=" + city + ", state=" + state
				+ ", pincode=" + pincode + ", dateCreated=" + dateCreated + ", userId=" + userId + "]";
	}
	public AddressDTO(long id, String line1, String line2, String city, String state, String pincode,
			LocalDateTime dateCreated, long userId) {
		this.id = id;
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.dateCreated = dateCreated;
		this.userId = userId;
	}
	public AddressDTO() {
		
	}
	
}
