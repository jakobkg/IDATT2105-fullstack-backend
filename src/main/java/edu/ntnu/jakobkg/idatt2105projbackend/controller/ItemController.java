package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Item;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.ItemRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/item")
public class ItemController {
    @Autowired
    private ItemRepository itemRepo;

    @PostMapping(path = "")
    public @ResponseBody Item addItem(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String date,
            @RequestParam String latitude,
            @RequestParam String longitude,
            @RequestParam String price,
            @RequestParam int categoryID,
            @RequestParam String images,
            @RequestParam int userID) {

        Item newItem = new Item(
                title,
                description,
                date,
                latitude,
                longitude,
                price,
                categoryID,
                images,
                userID);

        return itemRepo.save(newItem);
    }

    @PostMapping(path = "/{id}")
    public @ResponseBody Item updateItem(
            @RequestParam int itemID,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String date,
            @RequestParam String latitude,
            @RequestParam String longitude,
            @RequestParam String price,
            @RequestParam int categoryID,
            @RequestParam String images,
            @RequestParam int userID) {


        Item newItem = new Item(
                title,
                description,
                date,
                latitude,
                longitude,
                price,
                categoryID,
                images,
                userID);

        return itemRepo.save(newItem);
    }
}
