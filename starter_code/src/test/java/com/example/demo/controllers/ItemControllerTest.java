package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;

    private final ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepo);
    }

    @Test
    public void verify_getItemById() {
        when(itemRepo.findById(1L)).thenReturn(Optional.of(new Item()));
        ResponseEntity<Item> res = itemController.getItemById(1L);

        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());

        res = itemController.getItemById(2L);

        assertNotNull(res);
        assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void verify_getItemsByName() {
        when(itemRepo.findByName("item")).thenReturn(Arrays.asList(new Item()));
        ResponseEntity<List<Item>> res = itemController.getItemsByName("item");

        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());

        when(itemRepo.findByName("item")).thenReturn(Collections.emptyList());
        res = itemController.getItemsByName("item");

        assertNotNull(res);
        assertEquals(404, res.getStatusCodeValue());

        when(itemRepo.findByName("item")).thenReturn(null);
        res = itemController.getItemsByName("item");

        assertNotNull(res);
        assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void verify_getItems() {
        when(itemRepo.findAll()).thenReturn(null);

        ResponseEntity<List<Item>> res = itemController.getItems();

        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());

        when(itemRepo.findAll()).thenReturn(Collections.emptyList());

        res = itemController.getItems();

        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());

        when(itemRepo.findAll()).thenReturn(Arrays.asList(new Item()));

        res = itemController.getItems();

        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());
    }
}
