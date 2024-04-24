package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	@Query("select u from User u where length(u.name) >= :length and u.emailId like  %:emailId% ")
	List<User> findByCustomQuery(@Param("length") long length,@Param("emailId") String emailId);

	User findByEmailIdAndPassword(String emailId,String password);
	
	@Query("select count(u) > 0 from User u where u.name = :name and u.id != :id" )
	boolean existsByNameAndIdNot(@Param("name") String name,@Param("id") long id);
	
	@Query("select count(user) > 0 from User user where user.emailId = :emailId and user.id != :id" )
	boolean existByEmailIdAndIdNot(@Param("emailId") String emailId,@Param("id")long id);
}
