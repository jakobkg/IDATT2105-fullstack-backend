package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Item;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping(path = "/item")
public class ItemController {
    @Autowired
    private ItemRepository itemRepo;
    Logger logger = LoggerFactory.getLogger(ItemController.class);
    final int pageNum = 24;

    @PostMapping(path = "")
    public @ResponseBody Item add(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String date,
            @RequestParam String latitude,
            @RequestParam String longitude,
            @RequestParam String price,
            @RequestParam int categoryId,
            @RequestParam String images,
            @RequestParam int userId) {

        Item newItem = new Item(
                title,
                description,
                date,
                latitude,
                longitude,
                price,
                categoryId,
                images,
                userId);

        return itemRepo.save(newItem);
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody Item update(
            @PathVariable Integer id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String date,
            @RequestParam String latitude,
            @RequestParam String longitude,
            @RequestParam String price,
            @RequestParam int categoryId,
            @RequestParam String images,
            @RequestParam int userId) {


        Item newItem = new Item(
                title,
                description,
                date,
                latitude,
                longitude,
                price,
                categoryId,
                images,
                userId);

        return itemRepo.save(newItem);
    }

    @GetMapping(path = "")
    public Iterable<Item> getAll(@PathVariable int page) {
        page--; //zero-indexing on pages
        return itemRepo.findAll(PageRequest.of(page, this.pageNum));
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Item get(@PathVariable int id) {
        return itemRepo.findById(id).orElseThrow(()-> {
            logger.warn("No item found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }

    @GetMapping(path = "")
    public Iterable<Item> getAllBasedOnCategory(@PathVariable int categoryId, @PathVariable int page) {
        page--; //zero-indexing on pages
        return itemRepo.findAll(PageRequest.of(page, this.pageNum)).stream().filter(i->i.getCategoryID() == categoryId).toList();
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody void delete(@PathVariable int id) {
        itemRepo.deleteById(id);
    }

}
