package com.example.repository;

import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    List<User> findAllByName(String name);

    Optional<User> findByName(String name);

    Optional<User> findByVerificationCode(String verificationCode);

    Optional<User> findByEmail(String email);



    @Query(value = "select * from users u where u.name = ?1", nativeQuery = true)
    List<User> findAllByName1(String name);

    @Query(value = "select u from User u where u.name = :name")
    List<User> findAllByName2(String name);

}