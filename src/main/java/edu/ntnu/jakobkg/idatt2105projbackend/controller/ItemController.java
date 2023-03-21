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
    @ResponseStatus(HttpStatus.CREATED)
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
    public Iterable<Item> getMultiple(@RequestParam int page, @RequestParam(defaultValue="-1") int categoryId) {
        page--; //zero-indexing on pages

        //get based on category id
        if (categoryId < 0) {
            return itemRepo.findAll(PageRequest.of(page, this.pageNum)).stream().filter(i->i.getCategoryID() == categoryId).toList();
        // get all
        } else {
            return itemRepo.findAll(PageRequest.of(page, this.pageNum));
        }
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Item get(@RequestParam int id) {
        return itemRepo.findById(id).orElseThrow(()-> {
            logger.warn("No item found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void delete(@RequestParam int id) {
        itemRepo.deleteById(id);
    }

}
