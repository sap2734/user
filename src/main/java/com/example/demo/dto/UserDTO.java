package com.example.demo.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.CustomDateDeserializer;
import com.example.demo.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class UserDTO {
	private long id;
	private String name;
	private String emailId;
	private String password;
	private String gender;
	private String hobby;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
	private Date birthDate;
    private MultipartFile file;
   
   	public UserDTO(long id, String name, String emailId, String password, String gender, String hobby, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.emailId = emailId;
		this.password = password;
		this.gender = gender;
		this.hobby = hobby;
		this.birthDate = birthDate;
	}
    @Override
   	public String toString() {
   		return "UserDTO [id=" + id + ", name=" + name + ", emailId=" + emailId + ", password=" + password + ", gender="
   				+ gender + ", hobby=" + hobby + ", birthDate=" + birthDate + ", file=" + file + "]";
   	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public UserDTO(long id,String name) {
		super();
		this.id=id;this.name=name;
	}
	public UserDTO() {

	}
	
}
