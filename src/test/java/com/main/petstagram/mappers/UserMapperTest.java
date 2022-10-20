package com.main.petstagram.mappers;

import com.main.petstagram.dtos.UserDTO;
import com.main.petstagram.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    void toUserDto() {
        User testUser = new User();
        testUser.setId(-1L);
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setStatus("Status");
        testUser.setProfilePic("testurl");
        testUser.setEmail("Hello@testuser.com");

        UserDTO convertedUser = userMapper.toUserDto(testUser);

        Assertions.assertEquals(testUser.getId(), convertedUser.getId());
        Assertions.assertEquals(testUser.getFirstName(), convertedUser.getFirstName());
        Assertions.assertEquals(testUser.getEmail(), convertedUser.getEmail());
        Assertions.assertEquals(testUser.getProfilePic(), convertedUser.getProfilePic());
        Assertions.assertEquals(testUser.getStatus(), convertedUser.getStatus());
        Assertions.assertEquals(testUser.getLastName(), convertedUser.getLastName());
    }

    @Test
    void toUserDtoList() {
        User testUser = new User();
        testUser.setId(-1L);
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setStatus("Status");
        testUser.setProfilePic("testurl");
        testUser.setEmail("Hello@testuser.com");

        User testUser2 = new User();
        testUser2.setId(-2L);
        testUser2.setFirstName("Test2");
        testUser2.setLastName("User2");
        testUser2.setStatus("Status2");
        testUser2.setProfilePic("testurl2");
        testUser2.setEmail("Hello2@testuser.com");

        List<User> userList = new ArrayList<>();
        userList.add(testUser);
        userList.add(testUser2);

        List<UserDTO> userDTOS = userMapper.toUserDtoList(userList);

        for(int i = 0; i < userList.size(); i++){
            Assertions.assertEquals(userList.get(i).getId(), userDTOS.get(i).getId());
            Assertions.assertEquals(userList.get(i).getEmail(), userDTOS.get(i).getEmail());
            Assertions.assertEquals(userList.get(i).getFirstName(), userDTOS.get(i).getFirstName());
            Assertions.assertEquals(userList.get(i).getLastName(), userDTOS.get(i).getLastName());
            Assertions.assertEquals(userList.get(i).getStatus(), userDTOS.get(i).getStatus());
            Assertions.assertEquals(userList.get(i).getProfilePic(), userDTOS.get(i).getProfilePic());
        }
    }
}