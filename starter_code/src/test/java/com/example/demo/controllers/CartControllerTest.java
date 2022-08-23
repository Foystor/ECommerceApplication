package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;

    private final UserRepository userRepo = mock(UserRepository.class);

    private final CartRepository cartRepo = mock(CartRepository.class);

    private final ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepo);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepo);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepo);
    }

    @Test
    public void verify_addTocart() {
        ModifyCartRequest m = new ModifyCartRequest();
        m.setUsername("Admin");
        m.setItemId(1L);
        m.setQuantity(1);

        when(userRepo.findByUsername("Admin")).thenReturn(null);
        ResponseEntity<Cart> res = cartController.addTocart(m);

        assertNotNull(res);
        assertEquals(404, res.getStatusCodeValue());

        User user = new User();
        user.setCart(new Cart());
        when(userRepo.findByUsername("Admin")).thenReturn(user);
        when(itemRepo.findById(1L)).thenReturn(Optional.empty());
        res = cartController.addTocart(m);

        assertNotNull(res);
        assertEquals(404, res.getStatusCodeValue());

        Item item = new Item();
        item.setPrice(BigDecimal.valueOf(1));
        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));
        res = cartController.addTocart(m);

        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());
    }

    @Test
    public void removeFromcart() {
        ModifyCartRequest m = new ModifyCartRequest();
        m.setUsername("Admin");
        m.setItemId(1L);
        m.setQuantity(1);

        when(userRepo.findByUsername("Admin")).thenReturn(null);
        ResponseEntity<Cart> res = cartController.removeFromcart(m);

        assertNotNull(res);
        assertEquals(404, res.getStatusCodeValue());

        User user = new User();
        user.setCart(new Cart());
        when(userRepo.findByUsername("Admin")).thenReturn(user);
        when(itemRepo.findById(1L)).thenReturn(Optional.empty());
        res = cartController.removeFromcart(m);

        assertNotNull(res);
        assertEquals(404, res.getStatusCodeValue());

        Item item = new Item();
        item.setPrice(BigDecimal.valueOf(1));
        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));
        res = cartController.removeFromcart(m);

        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());
    }
}
