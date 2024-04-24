package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.CustomException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")

public class UserController {

	@Autowired
	private UserService userService;

	@PersistenceContext
	private EntityManager entity;

	@PostMapping("/create")
	public ResponseEntity<Object> createUser(@RequestBody UserDTO userDto,
			@RequestParam MultipartFile file) {
		if (userService.isNameExist(userDto.getName(), 0)) {
			return ResponseEntity.badRequest().body("User already exists");
		} else if (userService.isEmailIdExist(userDto.getEmailId(), 0)) {
			return ResponseEntity.badRequest().body("EmailId already exists");
		}
		if(file.isEmpty()) {
			return ResponseEntity.badRequest().body("please select file to upload");
		}
		if(file.getSize()>2090000) {
			return ResponseEntity.badRequest().body("file size exceed 2mb");
		}
        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            return new ResponseEntity<>("Only JPEG and PNG images are allowed.", HttpStatus.BAD_REQUEST);
        }
        userService.createUser(userDto);
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("message", "User created successfully");
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
	}

	@Transactional
	@PutMapping("/upload/{id}")
	public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file, @PathVariable("id") long id)
			throws IOException {
		try {
			String fileName = saveImage(file);
			String location = uploadPath + fileName;

			Query query = entity.createQuery("update User set imageLocation=:imageLocation where id=:id")
					.setParameter("imageLocation", location).setParameter("id", id);
			int updatedCount = query.executeUpdate();

			if (updatedCount == 1) {
				Map<String, String> responseBody = new HashMap<>();
				responseBody.put("message", "Image uploaded successfully");
				return ResponseEntity.ok(responseBody);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e);
		}
	}

	@Value("${upload.path}")
	private String uploadPath;

	public String saveImage(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("File is empty");
		}

		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path path = Paths.get(uploadPath + File.separator + fileName);
		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		return fileName;
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int userId) throws CustomException {
		UserDTO findUserById = userService.findUserById(userId);
		if (findUserById == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(findUserById, HttpStatus.OK);
		}
	}

	@GetMapping("all")
	public ResponseEntity<Map<String, Object>> getAllUserWithPage(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "3") int pageSize, @RequestParam(defaultValue = "") String name) {
		String nameParam = "%" + name + "%";
		TypedQuery<Long> count = entity.createQuery(
				"select count(u) from User u where lower(u.name) like lower(:name) or lower(u.emailId) like lower(:name)",
				Long.class).setParameter("name", nameParam);
		long totalCount = count.getSingleResult();
		TypedQuery<User> query = entity.createQuery(
				"select u from User u where lower(u.name) like lower(:name) or lower(u.emailId) like lower(:name)",
				User.class).setFirstResult(pageNumber * pageSize).setMaxResults(pageSize)
				.setParameter("name", nameParam);

		List<User> user = query.getResultList();
		int totalPages = (int) Math.ceil((double) totalCount / pageSize);
		Map<String, Object> result = new HashMap<>();
		result.put("user", user);
		result.put("totalPage", totalPages);
		if (user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(result);

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updatedUser(@RequestBody @Valid UserDTO userDto, @PathVariable("id") int id)
			throws CustomException {
		UserDTO userExistById = userService.findUserById(id);
		if (userExistById == null) {
			return ResponseEntity.badRequest().body("User not found for updation");
		}
		userDto.setId(id);
		if (userService.isNameExist(userDto.getName(), userDto.getId())) {
			return ResponseEntity.badRequest().body("User already exist");
		} else if (userService.isEmailIdExist(userDto.getEmailId(), userDto.getId())) {
			return ResponseEntity.badRequest().body("EmailId already exist");
		} else {
			userService.updateUser(userDto);
			Map<String, String> responseBody = new HashMap<>();
			responseBody.put("message", "User updated successfully");
			return ResponseEntity.status(HttpStatus.OK).body(responseBody);
		}
	}

	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable("id") int userId) throws Exception {
		userService.deleteUser(userId);

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO login) throws CustomException {
		try {
			User user = userService.getLogin(login.getEmailId(), login.getPassword());
			if (user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or password wrong 1@2");
			} else {
				return ResponseEntity.ok(user);
			}
		} catch (CustomException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the request");
		}
	}

	@GetMapping("/search/name")
	public boolean checkNameExists(@RequestParam(name = "name") String name,
			@RequestParam(name = "id", defaultValue = "0") int id) {
		return userService.isNameExist(name, id);
	}

	@GetMapping("/search/emailId")
	public boolean checkEmailIdExists(@RequestParam(name = "emailId") String emailId,
			@RequestParam(name = "id", defaultValue = "0") int id) {
		return userService.isEmailIdExist(emailId, id);
	}

	@GetMapping("/custom")
	public List<User> getUserUsingCustomQuery(@RequestParam(name = "length", defaultValue = "0") long length,
			@RequestParam(name = "emailId") String emailId) {
		return userService.customQueryForNameAndEmail(length, '@' + emailId);
	}
}
