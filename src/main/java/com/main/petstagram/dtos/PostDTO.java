package com.main.petstagram.dtos;

import com.ocpsoft.pretty.time.PrettyTime;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private UserDTO user;
    private String title;
    private String image;
    private Date date;
    private String prettyTime;
    private int commentCount;
}
