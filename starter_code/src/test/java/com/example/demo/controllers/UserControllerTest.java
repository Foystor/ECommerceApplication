package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private final UserRepository userRepo = mock(UserRepository.class);

    private final CartRepository cartRepo = mock(CartRepository.class);

    private final BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepo);
        TestUtils.injectObjects(userController, "cartRepository", cartRepo);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void create_user_happy_path() {
        when(encoder.encode("password")).thenReturn("hashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("password");
        r.setConfirmPassword("password");

        ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();

        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("test", u.getUsername());
        assertEquals("hashed", u.getPassword());
    }

    @Test
    public void create_user_bad_request_when_password_shorter_than_7() {
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("a");
        r.setConfirmPassword("a");

        ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void create_user_bad_request_when_password_not_equal_to_confirmPassword() {
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("password");
        r.setConfirmPassword("password2");

        ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void verify_findById() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(new User()));
        ResponseEntity<User> res = userController.findById(1L);

        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());

        res = userController.findById(2L);

        assertNotNull(res);
        assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void verify_findByUserName() {
        when(userRepo.findByUsername("Admin")).thenReturn(new User());
        ResponseEntity<User> res = userController.findByUserName("Admin");

        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());

        res = userController.findByUserName("Admin2");

        assertNotNull(res);
        assertEquals(404, res.getStatusCodeValue());
    }
}
