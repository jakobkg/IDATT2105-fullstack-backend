package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import edu.ntnu.jakobkg.idatt2105projbackend.model.AddItemRequest;
import edu.ntnu.jakobkg.idatt2105projbackend.model.Item;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.ItemRepository;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/item")
public class ItemController {
    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private UserRepository userRepo;

    Logger logger = LoggerFactory.getLogger(ItemController.class);
    final int pageSize = 24;

    /**
     * Add an item
     * @param request item object without id and user id
     * @return item object
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Item add(@RequestBody AddItemRequest request) {
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByEmail(username).orElseThrow(()-> {
            logger.warn("Item not found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        Item newItem = new Item(
                request.title(),
                request.description(),
                request.date(),
                request.latitude(),
                request.longitude(),
                request.price(),
                request.categoryId(),
                request.images(),
                user.getId());

        return itemRepo.save(newItem);
    }

    /**
     * Update an item
     * @param id item id
     * @param request item data
     * @return item object
     */
    @PutMapping("/{id}")
    public @ResponseBody Item update(@PathVariable Integer id, @RequestBody AddItemRequest request) {
        Item item = itemRepo.findById(id).orElseThrow(()-> {
            logger.warn("Item not found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userRepo.findByEmail(authenticatedUsername).orElseThrow();
        if (loggedInUser.getType() != User.UserType.ADMIN || loggedInUser.getId() != item.getUserId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        item.setTitle(request.title());
        item.setDescription(request.description());
        item.setDate(request.date());
        item.setLatitude(request.latitude());
        item.setLongitude(request.longitude());
        item.setPrice(request.price());
        item.setCategoryId(request.categoryId());
        item.setImages(request.images());

        return itemRepo.save(item);
    }

    /**
     * Get multiple items
     * @param page (optional)
     * @param categoryId (optional)
     * @param userId (optional)
     * @return all items or all items based on categoryId/userId
     */
    @GetMapping("")
    public Iterable<Item> getMultiple(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="-1") int categoryId, @RequestParam(defaultValue="-1") int userId) {
        page = page -1; //zero-indexing on pages
        if (page<0) {
            logger.warn("Page index must not be negative.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        logger.info("Request to get all items: "+" Page number: "+page+" Page size: "+pageSize+" Category id: "+categoryId);

        //get based on category id
        if (categoryId >= 0) {
            return itemRepo.findAll(PageRequest.of(page, this.pageSize)).stream().filter(i->i.getCategoryId() == categoryId).toList();
        // get based on user id
        } else if (userId >= 0) {
            return itemRepo.findAll(PageRequest.of(page, this.pageSize)).stream().filter(i->i.getUserId() == userId).toList();
        //get all
        } else {
            return itemRepo.findAll(PageRequest.of(page, this.pageSize));
        }
    }

    /**
     * Get single item based on id
     * @param id
     * @return item object
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Item get(@PathVariable int id) {
        return itemRepo.findById(id).orElseThrow(()-> {
            logger.warn("Item not found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }

    /**
     * Delete an item based on id
     * @param id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void delete(@PathVariable int id) {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userRepo.findByEmail(authenticatedUsername).orElseThrow();

        Item item = itemRepo.findById(id).orElseThrow(()-> {
            logger.warn("Item not found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        if (loggedInUser.getId() == item.getUserId() || loggedInUser.getType() == User.UserType.ADMIN) {
            itemRepo.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
