package com.main.petstagram.dtos;

import com.main.petstagram.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FriendRequestDTO {

    private Long id;
    private UserDTO sender;
    private UserDTO receiver;
    private Date date;

}
