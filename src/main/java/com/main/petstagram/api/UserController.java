package com.main.petstagram.api;

import com.main.petstagram.dtos.FriendRequestDTO;
import com.main.petstagram.dtos.UserDTO;
import com.main.petstagram.entities.User;
import com.main.petstagram.repos.UserRepo;
import com.main.petstagram.services.FriendRequestService;
import com.main.petstagram.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FriendRequestService friendRequestService;

    @GetMapping
    public List<UserDTO> getUsers(){
        return userService.getAllUserDtos();
    }

}
