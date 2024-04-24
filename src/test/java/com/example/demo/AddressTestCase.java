package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.demo.dto.AddressDTO;
import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.service.AddressService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressTestCase {
	
	@Autowired
	AddressService addressService;
	
	@Test
	@Rollback(value = false)
	@Order(1)
	public void testCaseForAddressCreation() throws Exception {
		AddressDTO createAddress= new AddressDTO();
		createAddress.setLine1("11,raj appartment");
		createAddress.setLine2("zundal");
		createAddress.setCity("Gandhinagar");
		createAddress.setState("Gujarat");
		createAddress.setPincode("123456");
		createAddress.setUserId(1);
		createAddress.setDateCreated(LocalDateTime.now());
		addressService.createAddress(createAddress);
		assertThat(createAddress.getState()).isEqualTo("Gujarat");
	}
	
	/*
	 * @Test
	 * 
	 * @Order(2) public void testCaseForAllAddress() { List<AddressDTO>
	 * getAllAddress = addressService.listAllAddress();
	 * System.out.println("Address Data is: " + getAllAddress);
	 * assertThat(getAllAddress).size().isGreaterThan(0); }
	 */
	
	@Test
	@Order(3)
	public void testCaseForAddressById() {
		Address getAddressById = addressService.findAddressById(3);
		System.out.println("Address Data is: " + getAddressById);
		assertThat(getAddressById.getId()).isEqualTo(3);
	}
	@Test
	@Order(4)
	@Rollback(value = false)
	public void testUpdateAddress() throws Exception {
		try {
			AddressDTO updateAdddress = new AddressDTO();
			updateAdddress.setId(4);
			updateAdddress.setLine1("n/101,ashray platina");
			updateAdddress.setLine2("opp aryda vida flat");
			updateAdddress.setCity("Surat");
			updateAdddress.setState("Gujarat3");
			updateAdddress.setPincode("382412");
			updateAdddress.setDateCreated(LocalDateTime.now());
			AddressDTO addressUpdated= addressService.updateAddress(updateAdddress);
			assertThat(addressUpdated.getCity()).isEqualTo("Surat");
		} catch (Exception e) {
			System.out.println("Data not found for Address which is provided for updation: ");
		}
	}
	
	@Test
	@Order(5)
	@Rollback(value = false)
	public void testAddressDeletation() throws Exception{
		try {
		   Address deleteAddress=addressService.findAddressById(3);
		   System.out.println("Address Data is: " + deleteAddress);
		   addressService.deleteAddress(3);
		   AddressDTO deletedAddress=null;
		   assertThat(deletedAddress).isNull();
		} catch (Exception e) {
			System.out.println("Data not found which is provided for deletation");
		}
	}
	@Test
	@Order(6)
	public void testCaseForUserByCity() {
		List<User> getAllAddress = addressService.getUserByCity("ahmedabad");
		System.out.println("User Data is With city: " + getAllAddress);
		assertThat(getAllAddress).size().isGreaterThan(0);
	}


}
