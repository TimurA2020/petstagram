package com.main.petstagram;

import com.main.petstagram.dtos.CommentDTO;
import com.main.petstagram.dtos.PostDTO;
import com.main.petstagram.dtos.UserDTO;
import com.main.petstagram.entities.Comment;
import com.main.petstagram.entities.Post;
import com.main.petstagram.entities.User;
import com.main.petstagram.mappers.CommentMapper;
import com.main.petstagram.mappers.PostMapper;
import com.main.petstagram.mappers.UserMapper;
import com.main.petstagram.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.util.Date;

@SpringBootTest
class PetstagramApplicationTests {

	@Test
	void contextLoads() {
	}

}
