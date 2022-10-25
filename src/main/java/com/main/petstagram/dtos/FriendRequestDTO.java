package com.main.petstagram.dtos;

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
