package com.main.petstagram.services;

import com.main.petstagram.entities.User;
import com.main.petstagram.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


//    @Test
//    void registerUser() {
//    }

    @Test
    void returnNullWhenCreatingWithoutEmail(){
        assertThrows(NullPointerException.class,
                () ->{
                    userService.registerUser("test", "user", null, null);
                });
    }

    @Test
    void updatePasswordCheck(){
        userService.registerUser("test", "user", "testuser@gmail.com", "testuser");
        User foundedUser = userService.findByEmail("testuser@gmail.com");
        userService.updatePassword(foundedUser, "testuser", "usertest");
        User testedPasswordUser = userService.findByEmail("testuser@gmail.com");
        Assertions.assertTrue(passwordEncoder.matches("usertest", testedPasswordUser.getPassword()));
    }


    @Test
    void checkUserRemoval(){
        userService.registerUser("test", "user", "testuser@gmail.com", "testuser");
        Long user_id = userService.findByEmail("testuser@gmail.com").getId();
        userService.deleteUser(user_id);
        Assertions.assertNull(userService.findByEmail("testuser@gmail.com"));
    }

    @Test
    void checkUpdateCredentials(){
        userService.registerUser("test", "user", "testuser@gmail.com", "testuser");
        User foundedUser = userService.findByEmail("testuser@gmail.com");
        String updatedFirstName = "test2";
        String updatedLastName = "user2";
        String updatedStatus = "Great to be here!";
        userService.updateCredentials(foundedUser, updatedFirstName, updatedLastName, updatedStatus);
        User updatedUser = userService.findByEmail("testuser@gmail.com");

        Assertions.assertEquals(updatedUser.getFirstName(), updatedFirstName);
        Assertions.assertEquals(updatedUser.getLastName(), updatedLastName);
        Assertions.assertEquals(updatedUser.getStatus(), updatedStatus);
    }

}