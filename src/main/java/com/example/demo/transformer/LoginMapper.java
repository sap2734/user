package com.example.demo.transformer;

import org.springframework.stereotype.Component;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.model.LoginRequest;
@Component
public class LoginMapper {

	public LoginRequestDTO convertToDTO(LoginRequest loginRequest) {
		LoginRequestDTO loginRequestDto=new LoginRequestDTO(
		loginRequest.getEmailId(),
		loginRequest.getPassword());
		return loginRequestDto;
	}
	
	public LoginRequest convertToEntity(LoginRequestDTO loginRequestDto) {
		LoginRequest loginRequest=new LoginRequest(
		loginRequestDto.getEmailId(),
		loginRequestDto.getPassword());
		return loginRequest;
	}
}
