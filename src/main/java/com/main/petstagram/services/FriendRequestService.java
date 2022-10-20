package com.main.petstagram.services;

import com.main.petstagram.dtos.FriendRequestDTO;
import com.main.petstagram.entities.FriendRequest;
import com.main.petstagram.entities.User;
import com.main.petstagram.mappers.FriendRequestMapper;
import com.main.petstagram.repos.FriendRequestRepo;
import com.main.petstagram.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
public class FriendRequestService {

    @Autowired
    private FriendRequestRepo friendRequestRepo;

    @Autowired
    private FriendRequestMapper friendRequestMapper;

    @Autowired
    private UserRepo userRepo;

    public FriendRequest getFriendRequest(Long id){
        return friendRequestRepo.findById(id).orElseThrow();
    }

    public FriendRequest addFriendRequest(Long sender_id, Long receiver_id){
        FriendRequest friendRequest = new FriendRequest();
        User sender = userRepo.findById(sender_id).orElseThrow();
        User receiver = userRepo.findById(receiver_id).orElseThrow();
        friendRequest.setDate(new Date());
        friendRequest.setReceiver(receiver);
        friendRequest.setSender(sender);
        return friendRequestRepo.save(friendRequest);
    }

    public void addAsFriend(Long id){
        FriendRequest friendRequest = friendRequestRepo.findById(id).orElseThrow();
        User sender = userRepo.findById(friendRequest.getSender().getId()).orElseThrow();
        User receiver = userRepo.findById(friendRequest.getReceiver().getId()).orElseThrow();
        List<User> senderFriends = sender.getFriends();
        senderFriends.add(receiver);
        sender.setFriends(senderFriends);
        List<User> receiverFriends = receiver.getFriends();
        receiverFriends.add(sender);
        receiver.setFriends(receiverFriends);
        userRepo.save(sender);
        userRepo.save(receiver);
        friendRequestRepo.delete(friendRequest);
    }

    public boolean checkRequest(Long sender_id, Long receiver_id){
        List<FriendRequest> friendRequestsFromSender = friendRequestRepo
                .findFriendRequestBySenderId(sender_id);

        for(int i = 0; i < friendRequestsFromSender.size(); i++){
            if(friendRequestsFromSender.get(i).getReceiver().getId().equals(receiver_id)){
                return true;
            }
        }
        return false;
    }

    public void declineRequest(Long id){
        FriendRequest friendRequest = friendRequestRepo.findById(id).orElseThrow();
        friendRequestRepo.delete(friendRequest);
    }

    public List<FriendRequestDTO> getFriendRequests(Long id){
        return friendRequestMapper.toFriendRequestDTOs(friendRequestRepo.findFriendRequestByReceiverId(id));
    }

    public void deleteFriend(Long currentUserId, Long friendId){
        //Getting both Current User and the friend we want to remove
        User currentUser = userRepo.findById(currentUserId).orElseThrow();
        User friend = userRepo.findById(friendId).orElseThrow();

        currentUser.removeFriend(friend);
        userRepo.save(currentUser);
    }

    public void deleteRequestAssociatedWithUser(Long user_id){
        friendRequestRepo.deleteAll(friendRequestRepo.findFriendRequestByReceiverId(user_id));
        friendRequestRepo.deleteAll(friendRequestRepo.findFriendRequestBySenderId(user_id));
    }


}
