package com.main.petstagram.repos;

import com.main.petstagram.entities.FriendRequest;
import com.main.petstagram.entities.User;
import com.main.petstagram.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FriendRequestRepoTest {

    @Autowired
    private FriendRequestRepo friendRequestRepo;

    @Autowired
    private UserRepo userRepo;

    @Test
    void testInsertionToDataBase(){
        FriendRequest friendRequest = new FriendRequest();

        //sender
        User testUser = new User();
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setStatus("Status");
        testUser.setProfilePic("testurl");
        String email = "Hello@testuser.com";
        testUser.setEmail(email);

        User receiver = new User();
        receiver.setFirstName("receiver");
        receiver.setLastName("receiver");
        receiver.setStatus("receiver");
        receiver.setProfilePic("receiver");
        String emailReceiver = "receiver@testuser.com";
        receiver.setEmail(emailReceiver);

        userRepo.save(testUser);
        userRepo.save(receiver);

        friendRequest.setDate(new Date());

        friendRequest.setSender(userRepo.findAllByEmail(email));
        friendRequest.setReceiver(userRepo.findAllByEmail(emailReceiver));

        FriendRequest addedFriendRequest = friendRequestRepo.save(friendRequest);

        Assertions.assertNotNull(addedFriendRequest);
        Assertions.assertEquals(addedFriendRequest.getDate(), friendRequest.getDate());
        Assertions.assertEquals(addedFriendRequest.getReceiver().getFirstName(), friendRequest.getReceiver().getFirstName());
        Assertions.assertEquals(addedFriendRequest.getReceiver().getLastName(), friendRequest.getReceiver().getLastName());
        Assertions.assertEquals(addedFriendRequest.getReceiver().getEmail(), friendRequest.getReceiver().getEmail());
        Assertions.assertEquals(addedFriendRequest.getSender().getFirstName(), friendRequest.getSender().getFirstName());
        Assertions.assertEquals(addedFriendRequest.getSender().getLastName(), friendRequest.getSender().getLastName());
        Assertions.assertEquals(addedFriendRequest.getSender().getEmail(), friendRequest.getSender().getEmail());

    }


    @Test
    void checkFindFriendRequestByReceiverId(){
        FriendRequest friendRequest = new FriendRequest();

        //sender
        User testUser = new User();
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setStatus("Status");
        testUser.setProfilePic("testurl");
        String email = "Hello@testuser.com";
        testUser.setEmail(email);

        User receiver = new User();
        receiver.setFirstName("receiver");
        receiver.setLastName("receiver");
        receiver.setStatus("receiver");
        receiver.setProfilePic("receiver");
        String emailReceiver = "receiver@testuser.com";
        receiver.setEmail(emailReceiver);

        userRepo.save(testUser);
        userRepo.save(receiver);

        friendRequest.setDate(new Date());

        User senderAdded = userRepo.findAllByEmail(email);
        User receiverAdded = userRepo.findAllByEmail(emailReceiver);

        friendRequest.setSender(senderAdded);
        friendRequest.setReceiver(receiverAdded);

        FriendRequest addedFriendRequest = friendRequestRepo.save(friendRequest);

        List<FriendRequest> friendRequests = friendRequestRepo.findFriendRequestByReceiverId(receiver.getId());

        Assertions.assertNotNull(friendRequests);
        Assertions.assertEquals(friendRequests.get(0).getDate(), friendRequest.getDate());
        Assertions.assertEquals(friendRequests.get(0).getReceiver(), receiverAdded);
        Assertions.assertEquals(friendRequests.get(0).getSender(), senderAdded);
    }

    @Test
    void checkFindFriendRequestBySenderId(){
        FriendRequest friendRequest = new FriendRequest();

        //sender
        User testUser = new User();
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setStatus("Status");
        testUser.setProfilePic("testurl");
        String email = "Hello@testuser.com";
        testUser.setEmail(email);

        User receiver = new User();
        receiver.setFirstName("receiver");
        receiver.setLastName("receiver");
        receiver.setStatus("receiver");
        receiver.setProfilePic("receiver");
        String emailReceiver = "receiver@testuser.com";
        receiver.setEmail(emailReceiver);

        userRepo.save(testUser);
        userRepo.save(receiver);

        friendRequest.setDate(new Date());

        User senderAdded = userRepo.findAllByEmail(email);
        User receiverAdded = userRepo.findAllByEmail(emailReceiver);

        friendRequest.setSender(senderAdded);
        friendRequest.setReceiver(receiverAdded);

        FriendRequest addedFriendRequest = friendRequestRepo.save(friendRequest);

        List<FriendRequest> friendRequests = friendRequestRepo.findFriendRequestBySenderId(senderAdded.getId());

        Assertions.assertNotNull(friendRequests);
        Assertions.assertEquals(friendRequests.get(0).getDate(), friendRequest.getDate());
        Assertions.assertEquals(friendRequests.get(0).getReceiver(), receiverAdded);
        Assertions.assertEquals(friendRequests.get(0).getSender(), senderAdded);

    }


}