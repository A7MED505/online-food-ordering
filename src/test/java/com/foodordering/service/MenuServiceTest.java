package com.foodordering.service;

import com.foodordering.model.MenuItem;
import com.foodordering.repository.MenuItemRepository;
import com.foodordering.util.DbTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class MenuServiceTest {

    private MenuItemRepository repository;
    private MenuService service;

    @BeforeEach
    void setup() throws Exception {
        DbTestUtil.clearAll();
        repository = new MenuItemRepository();
        service = new MenuService(repository);
    }

    @Test
    void addAndFindByName() throws Exception {
        service.addItem(new MenuItem("1", "Burger", 10.0));

        Optional<MenuItem> found = service.findByName("burger");
        assertTrue(found.isPresent());
        assertEquals("Burger", found.get().getName());
        assertEquals(1, repository.count());
    }
}
