package com.main.petstagram.repos;

import com.main.petstagram.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @AfterEach
    void deleteData(){
        userRepo.deleteAll();
    }

    //Testing if out repo can find by email, and it's the exact user we want to find
    @Test
    void findAllByEmail() {
        User testUser = new User();
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setStatus("Status");
        testUser.setProfilePic("testurl");
        String email = "Hello@testuser.com";
        testUser.setEmail(email);

        userRepo.save(testUser);

        User foundedUser = userRepo.findAllByEmail(email);

        Assertions.assertNotNull(foundedUser);
        Assertions.assertEquals(testUser.getFirstName(), foundedUser.getFirstName());
        Assertions.assertEquals(testUser.getLastName(), foundedUser.getLastName());
        Assertions.assertEquals(testUser.getStatus(), foundedUser.getStatus());
        Assertions.assertEquals(testUser.getProfilePic(), foundedUser.getProfilePic());
        Assertions.assertEquals(testUser.getEmail(), foundedUser.getEmail());
        Assertions.assertEquals(testUser.getPassword(), foundedUser.getPassword());
    }

    @Test
    void testUserInsertion(){

        User testUser = new User();
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setStatus("Status");
        testUser.setProfilePic("testurl");
        String email = "Hello@testuser.com";
        testUser.setEmail(email);

        User insertedUser = userRepo.save(testUser);

        Assertions.assertNotNull(insertedUser);
        Assertions.assertEquals(testUser.getFirstName(), insertedUser.getFirstName());
        Assertions.assertEquals(testUser.getStatus(), insertedUser.getStatus());
        Assertions.assertEquals(testUser.getEmail(), insertedUser.getEmail());
        Assertions.assertEquals(testUser.getPassword(), insertedUser.getPassword());
        Assertions.assertEquals(testUser.getProfilePic(), insertedUser.getProfilePic());

    }




}