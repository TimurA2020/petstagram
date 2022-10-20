package com.main.petstagram.dtos;

import com.main.petstagram.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private List<Role> roles;
    private String profilePic;
    private String status;
}
