package com.arm.demo.service;

import com.arm.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDao extends JpaRepository<User, String> {

    User getUserByAge(int age);

    @Query("select u from User u where u.age = ?1")
    List<User> findByAge(Integer age);

    @Query("select max(age) from User order by age")
    int getMaxAge();


    @Modifying
    @Query("delete from User u where u.id in ?1")
    int deleteByIdIn(List<String> ids);

}
