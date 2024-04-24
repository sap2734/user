package com.example.demo.transformer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.dto.AddressDTO;
import com.example.demo.model.Address;

@Component
public class AddressTransformer {
	
	public AddressDTO convertToDTO(Address address) {
		System.out.println("Address transformer: " +address);
		AddressDTO addressDto=new AddressDTO();
		addressDto.setId(address.getId());
		addressDto.setLine1(address.getLine1());
		addressDto.setLine2(address.getLine2());
		addressDto.setCity(address.getCity());
		addressDto.setState(address.getState());
		addressDto.setPincode(address.getPincode());
		addressDto.setDateCreated(LocalDateTime.now());
		addressDto.setUserId(address.getUser().getId());
		return addressDto;
	}
	
	public Address convertToEntity(AddressDTO addressDto) {
		Address address=new Address();
		address.setId(addressDto.getId());
		address.setLine1(addressDto.getLine1());
		address.setLine2(addressDto.getLine2());
		address.setCity(addressDto.getCity());
		address.setState(addressDto.getState());
		address.setPincode(addressDto.getPincode());
		address.setDateCreated(LocalDateTime.now());
		return address;
	}

}
