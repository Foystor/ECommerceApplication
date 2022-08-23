package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;

    private final UserRepository userRepo = mock(UserRepository.class);

    private final OrderRepository orderRepo = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepo);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepo);
    }

    @Test
    public void verify_getOrdersForUser() {
        when(userRepo.findByUsername("Admin")).thenReturn(null);

        ResponseEntity<List<UserOrder>> res = orderController.getOrdersForUser("Admin");

        assertNotNull(res);
        assertEquals(404, res.getStatusCodeValue());

        when(userRepo.findByUsername("Admin")).thenReturn(new User());

        res = orderController.getOrdersForUser("Admin");

        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());
    }


    @Test
    public void verify_submit() {
        when(userRepo.findByUsername("Admin")).thenReturn(null);

        ResponseEntity<UserOrder> res = orderController.submit("Admin");

        assertNotNull(res);
        assertEquals(404, res.getStatusCodeValue());

        User user = new User();
        Cart cart = new Cart();
        cart.setItems(Arrays.asList(new Item()));
        user.setCart(cart);
        when(userRepo.findByUsername("Admin")).thenReturn(user);

        res = orderController.submit("Admin");

        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());
    }
}
