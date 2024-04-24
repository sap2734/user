package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.CustomException;
import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.transformer.UserTransformer;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserTransformer userTransformer;

	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public UserDTO createUser(UserDTO userDTO) throws CustomException {
		try {
			User user = userTransformer.convertToEntity(userDTO);
			user= userRepository.save(user);
			return userTransformer.convertToDTO(user);
			
		} catch (IOException e) {
		System.out.println(e);
		}
		return userDTO;
			
		}
	@Override
	public Page<UserDTO> getAllUser(Pageable pageable) {
		Page<User> userPage = userRepository.findAll(pageable);
	    List<UserDTO> userDTOList = userPage.getContent().stream()
	            .map(userTransformer::convertToDTO)
	            .collect(Collectors.toList());
	    return new PageImpl<>(userDTOList, pageable, userPage.getTotalElements());

	}
	@Override
	public UserDTO updateUser(UserDTO userDto) throws CustomException {
		try {
			User user = userTransformer.convertToEntity(userDto);
		user= userRepository.save(user);
		return userTransformer.convertToDTO(user);
	} catch (IOException e) {
		System.out.println(e);
		}
		return userDto;
		

	}
	@Override
	public void deleteUser(long id) throws CustomException {
		userRepository.findById(id).orElseThrow(() -> new CustomException("User not found with User Id " + id));
		Address userExistOnAddress = addressRepository.findByUserId(id);
		if (userExistOnAddress == null) {
			userRepository.deleteById(id);
		} else {
			addressRepository.deleteById(userExistOnAddress.getId());
			userRepository.deleteById(id);
		}
	}

	@Override
	public UserDTO findUserById(long id){
		User user = userRepository.findById(id).orElseThrow(() -> new CustomException("User not found"));
		return userTransformer.convertToDTO(user);
	}

	@Override
	public User getLogin(String emailId,String password) throws CustomException {
		return userRepository.findByEmailIdAndPassword(emailId,password);
	}

	@Override
	public boolean isNameExist(String name, long id) {
		return userRepository.existsByNameAndIdNot(name, id);
	}
	@Override
	public boolean isEmailIdExist(String emailId, long id) {
		return userRepository.existByEmailIdAndIdNot(emailId, id);
	}
	
	@Override
	public List<User> customQueryForNameAndEmail(long length,String emailId) {
		List<User> user = new ArrayList<User>();
		userRepository.findByCustomQuery(length,emailId).forEach(user::add);
		if (user.isEmpty()) {
			throw new CustomException("User not found");
		}
		else
		{
		return user;
		}
	}
	}
