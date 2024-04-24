package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.AddressDTO;
import com.example.demo.model.Address;
import com.example.demo.model.User;

@Service
public interface AddressService {
	
	Address findAddressById(long id);
	
	AddressDTO createAddress(AddressDTO addressDto) throws Exception;
	
	AddressDTO updateAddress(AddressDTO addressDto);
	
	void deleteAddress(long id);

	List<User> getUserByCity(String city);

	}
