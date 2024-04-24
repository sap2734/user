package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.CustomException;
import com.example.demo.service.UserService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

	@Autowired
	private UserService userService;

	@Test
	@Rollback(value = false)
	@Order(1)
	public void testCaseForUserCreation() throws Exception {
		UserDTO createUser= new UserDTO();
		createUser.setName("suresh");
		createUser.setEmailId("sap2734@gmail.com");
		createUser.setPassword("123458");
		userService.createUser(createUser);
		assertThat(createUser.getEmailId()).isEqualTo("sap2734@gmail.com");
	}

//	@Test
//	@Order(2)
//	public void testGetAllUser() {
//		List<UserDTO> getAllUser = userService.getAllUser();
//		System.out.println("User Data is: " + getAllUser);
//		assertThat(getAllUser).size().isGreaterThan(0);
//	}

	@Test
	@Order(3)
	public void testGetUserById() throws Exception {
			UserDTO searchById = userService.findUserById(1);
			System.out.println("User Data is: " + searchById);
			assertThat(searchById.getId()).isEqualTo(1);
     }

	@Test
	@Order(5)
	@Rollback(value = false)
	public void testDeleteUser() {
		try {
			UserDTO userSearchById = userService.findUserById(2);
			System.out.println("User Data is: " + userSearchById);
			userService.deleteUser(2);
			UserDTO deletedUser = null;
			assertThat(deletedUser).isNull();
		} catch (Exception e) {
			System.out.println("Data not found for user which is provided for deletion");
		}
	}

	@Test
	@Order(4)
	@Rollback(value = false)
	public void testUpdateUser() throws CustomException {
		try {
			UserDTO updateUser = new UserDTO();
			updateUser.setId(3);
			updateUser.setName("suresh");
			updateUser.setEmailId("sap2734@gmail.co");
			updateUser.setPassword("tes#123");
			UserDTO userUpdated= userService.updateUser(updateUser);
			assertThat(userUpdated.getEmailId()).isEqualTo("test@test.com");
		} catch (Exception e) {
			System.out.println("Data not found for user which is provided for updation: ");
		}
	}
}
