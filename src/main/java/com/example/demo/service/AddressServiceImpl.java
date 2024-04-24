package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddressDTO;
import com.example.demo.exception.CustomException;
import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.transformer.AddressTransformer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
@Transactional
@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	public AddressRepository addressRepository;
	
	@Autowired
	public UserRepository userRepository;
		
	@Autowired
	public AddressTransformer addressTransformer;
	
	@PersistenceContext
	private EntityManager entity;
	
	@Override
	public Address findAddressById(long id) {
		Address address = addressRepository.findById(id).orElseThrow(()->
		new IllegalArgumentException("Address not found"));
		return address;
	}

	@Override
	public AddressDTO createAddress(AddressDTO addressDto) {
	    User user = userRepository.findById(addressDto.getUserId()).orElseThrow(()->new IllegalArgumentException("User not found with User Id: " +addressDto.getUserId()));
	   	Address address = addressTransformer.convertToEntity(addressDto);
		address.setUser(user);
		Address updatedAddress=addressRepository.save(address);
		return addressTransformer.convertToDTO(updatedAddress);
	}

	@Override
	public AddressDTO updateAddress(AddressDTO addressDto) {
		addressRepository.findById(addressDto.getId()).orElseThrow(()->
		new IllegalArgumentException("Address not found"));
		//fetch user id
	    User userId = userRepository.findById(addressDto.getUserId()).orElseThrow(()-> new IllegalArgumentException("User not found wit user Id: "+addressDto.getUserId()));
		Address address=addressTransformer.convertToEntity(addressDto);
		address.setUser(userId);
		Address updateAddress = addressRepository.save(address);
		return addressTransformer.convertToDTO(updateAddress);
	}

	@Override
	public void deleteAddress(long id) {
		addressRepository.findById(id).orElseThrow(()->
		new IllegalArgumentException("Address not found"));
		addressRepository.deleteById(id);
	}
	@Override
	public List<User> getUserByCity(String city) {
		String pql="select a.user from Address a where a.city = :city and left(a.user.name,1) = upper(left(a.user.name,1))";
		
		//String pql = "select u from User u where u.id in (select a.user.id from Address a where a.city = :city)";
        TypedQuery<User> query = entity.createQuery(pql,User.class);
        query.setParameter("city", city);
        if(query.getResultList().isEmpty()) {
        	throw new CustomException("Data not found");
        }
        else {
        return query.getResultList();
        }
	}
	}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           