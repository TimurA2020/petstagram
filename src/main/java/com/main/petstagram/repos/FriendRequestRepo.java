package com.main.petstagram.repos;

import com.main.petstagram.entities.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FriendRequestRepo extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findFriendRequestByReceiverId(Long id);

    List<FriendRequest> findFriendRequestBySenderId(Long id);

}
