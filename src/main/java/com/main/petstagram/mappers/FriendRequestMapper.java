package com.main.petstagram.mappers;

import com.main.petstagram.dtos.FriendRequestDTO;
import com.main.petstagram.entities.FriendRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {

    FriendRequestDTO toFriendRequestDTO(FriendRequest friendRequest);

    List<FriendRequestDTO> toFriendRequestDTOs(List<FriendRequest> friendRequests);
}
