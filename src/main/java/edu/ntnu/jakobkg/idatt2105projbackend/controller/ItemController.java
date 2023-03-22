package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Item;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/item")
public class ItemController {
    @Autowired
    private ItemRepository itemRepo;
    Logger logger = LoggerFactory.getLogger(ItemController.class);
    final int pageNum = 24;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Item add(@RequestBody Item item) {
        Item newItem = new Item(
                item.getTitle(),
                item.getDescription(),
                item.getDate(),
                item.getLatitude(),
                item.getLongitude(),
                item.getPrice(),
                item.getCategoryId(),
                item.getImages(),
                item.getUserId());
        return itemRepo.save(newItem);
    }

    @PutMapping("/{id}")
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


        itemRepo.findById(id).orElseThrow(()-> {
            logger.warn("Item not found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        Item updatedItem = new Item(
                id,
                title,
                description,
                date,
                latitude,
                longitude,
                price,
                categoryId,
                images,
                userId);
        return itemRepo.save(updatedItem);

    }

    @GetMapping("")
    public Iterable<Item> getMultiple(@RequestParam int page, @RequestParam(defaultValue="-1") int categoryId) {
        page--; //zero-indexing on pages

        //get based on category id
        if (categoryId < 0) {
            return itemRepo.findAll(PageRequest.of(page, this.pageNum)).stream().filter(i->i.getCategoryId() == categoryId).toList();
        // get all
        } else {
            return itemRepo.findAll(PageRequest.of(page, this.pageNum));
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Item get(@PathVariable int id) {
        return itemRepo.findById(id).orElseThrow(()-> {
            logger.warn("Item not found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void delete(@PathVariable int id) {
        itemRepo.deleteById(id);
    }

}
