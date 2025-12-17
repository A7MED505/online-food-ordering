package com.foodordering.service;

import com.foodordering.model.MenuItem;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class MenuServiceTest {

    @Test
    void addAndFindByName() {
        MenuService service = new MenuService();
        service.addItem(new MenuItem("1", "Burger", 10.0));

        Optional<MenuItem> found = service.findByName("burger");
        assertTrue(found.isPresent());
        assertEquals("Burger", found.get().getName());
    }
}
