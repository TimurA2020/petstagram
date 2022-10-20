package com.main.petstagram.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String comment;
    private Date date;
    private String prettyTime;
    private UserDTO user;
    private PostDTO post;
}
