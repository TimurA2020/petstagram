package com.main.petstagram.mappers;

import com.main.petstagram.dtos.UserDTO;
import com.main.petstagram.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDto(User user);
    List<UserDTO> toUserDtoList(List<User> userList);

}
