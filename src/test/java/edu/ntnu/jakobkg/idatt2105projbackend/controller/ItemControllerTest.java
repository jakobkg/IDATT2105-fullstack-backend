package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Item;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.ItemRepository;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemController itemController;

    Item testItem = new Item("title", "description", "date", "lat", "long", "location", "price", 1, "images", 1);

    @Test
    public void findByCategoryWorks() {
        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList.add(testItem);
        itemRepository.save(testItem);
        when(itemRepository.findByCategoryId(1, PageRequest.of(0, 24))).thenReturn(arrayList);

        assertEquals(itemController.getMultiple(0, 1, -1), arrayList);
    }

}
