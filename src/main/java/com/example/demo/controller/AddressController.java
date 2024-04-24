package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AddressService;


@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/create")
	public ResponseEntity<Object> createAddress(@RequestBody @Validated AddressDTO addressDto) throws Exception{
		addressService.createAddress(addressDto);
		Map<String, String> responseBody = new HashMap<>();
	    responseBody.put("message", "Address created successfully");
	    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Object> getAddressById(@PathVariable("id") int addressId) {
		Address findAddressById = addressService.findAddressById(addressId);
		if (findAddressById == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(findAddressById, HttpStatus.OK);
		}
	}
	@GetMapping("/all")
	public ResponseEntity<Page<Address>> getAllAddress(Pageable pagable) {
		Page<Address> itemsPage = addressRepository.findAll(pagable);
        if(itemsPage.isEmpty()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(itemsPage);
		
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updatedAddress(@RequestBody @Validated AddressDTO addressDto,@PathVariable("id") int addressId) throws Exception {
		addressDto.setId(addressId);
		addressService.updateAddress(addressDto);
		Map<String, String> responseBody = new HashMap<>();
	    responseBody.put("message", "Address updated successfully");
	    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
	}
	@DeleteMapping("/delete/{id}")
	public void deleteAddress(@PathVariable("id") int addressId) throws Exception{
		addressService.deleteAddress(addressId);
	}
	@GetMapping("/custom")
	public List<User> getUserUsingAddress(@RequestParam(name = "city") String city) {
		return addressService.getUserByCity(city);
	}
	@GetMapping("/getuser")
	public List<UserDTO> getAllUserNotExistOnAddress() {
		List<Long> userIdInAddress=addressRepository.findAll().stream()
			.map(address ->address.getUser().getId())
			.collect(Collectors.toList());
		return userRepository.findAll().stream()
				.filter(user ->!userIdInAddress.contains(user.getId()))
				.map(user ->new UserDTO(user.getId(),user.getName()))
				.collect(Collectors.toList());
	}
}

