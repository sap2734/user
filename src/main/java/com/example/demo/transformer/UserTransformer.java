package com.example.demo.transformer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

@Component
public class UserTransformer {
	public UserDTO convertToDTO(User user) {
		UserDTO userDTO=new UserDTO(
		user.getId(),
		user.getName(),
		user.getEmailId(),
		user.getPassword(),
		user.getGender(),
		user.getHobby(),
		user.getBirthDate());
		return userDTO;
	}
	
	public User convertToEntity(UserDTO userDTO) throws IOException{
		User user=new User(
		userDTO.getId(),
		userDTO.getName(),
		userDTO.getEmailId(),
		userDTO.getPassword(),
		userDTO.getGender(),
		userDTO.getHobby(),
		userDTO.getBirthDate(),
		userDTO.getFile().getBytes());
		return user;
	}

}
