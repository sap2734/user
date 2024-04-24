package com.example.demo.model;


import java.util.Date;

import com.example.demo.CustomDateDeserializer;
import com.example.demo.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "user_info")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotEmpty(message = "Please enter name")
	private String name;
	@NotEmpty(message = "Please enter email address")
	@Email(message = "Please enter valid email address")
	private String emailId;
	@NotEmpty(message = "please enter password")
	private String password;
	@NotEmpty(message = "please select gender")
	private String gender;
	@NotEmpty(message = "please select hobby")
	private String hobby;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
 	private Date birthDate;
    @Lob
    private byte[] imageName;
   
   	public User(long id, String name, String emailId, String password, String gender, String hobby, Date birthDate,byte[] imageName) {
		super();
		this.id = id;
		this.name = name;
		this.emailId = emailId;
		this.password = password;
		this.gender = gender;
		this.hobby = hobby;
		this.birthDate = birthDate;
		this.imageName=imageName;
		}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", emailId=" + emailId + ", password=" + password + ", gender="
				+ gender + ", hobby=" + hobby + ", birthDate=" + birthDate + "]";
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
	 public byte[] getImageName() {
			return imageName;
	}
	public void setImageName(byte[] imageName) {
			this.imageName = imageName;
	}

	public User() {
		
	}
}
