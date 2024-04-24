package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.CustomException;
import com.example.demo.model.User;
@Service
public interface UserService {

	UserDTO createUser(UserDTO userDTO) throws CustomException;
    Page<UserDTO> getAllUser(Pageable pageable);
    UserDTO findUserById(long id) throws CustomException;
    UserDTO updateUser(UserDTO updateUser) throws CustomException;
    void deleteUser(long id) throws CustomException;
    User getLogin(String emailId,String password) throws CustomException;
 	boolean isNameExist(String name, long id);
	boolean isEmailIdExist(String emailId, long id);
	List<User> customQueryForNameAndEmail(long length,String emailId);
	}
