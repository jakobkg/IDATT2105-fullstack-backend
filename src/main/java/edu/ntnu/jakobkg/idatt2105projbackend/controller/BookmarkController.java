package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Bookmark;
import edu.ntnu.jakobkg.idatt2105projbackend.model.BookmarkId;
import edu.ntnu.jakobkg.idatt2105projbackend.model.Item;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.BookmarkRepository;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.ItemRepository;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    Logger logger = LoggerFactory.getLogger(BookmarkController.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    ItemRepository itemRepository;

    /**
     * Adds a bookmark.
     *
     * @param token  jwt identifying the user
     * @param itemId the item id
     * @return the bookmark
     */
    @PostMapping(path = "/{itemId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Bookmark addBookmark(@RequestHeader("Authorization") String token, @PathVariable int itemId) {

        // Authentication
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedinUser = userRepo.findByEmail(authenticatedUsername).orElseThrow();

        Item chosenItem = itemRepository.findById(itemId).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        if (!bookmarkRepository.existsById(new BookmarkId(loggedinUser.getId(), chosenItem.getId())))
            return bookmarkRepository.save(new Bookmark(new BookmarkId(loggedinUser.getId(), chosenItem.getId())));

        return bookmarkRepository.findById(new BookmarkId(loggedinUser.getId(), chosenItem.getId())).orElseThrow(); // if this runs,
                                                                                                    // bookmark already
                                                                                                    // exists
    }

    /**
     * Deletes a bookmark.
     *
     * @param token  jwt identifying the user
     * @param itemId the item id
     */
    @DeleteMapping(path = "/{itemId}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody void deleteBookmark(@RequestHeader("Authorization") String token, @PathVariable int itemId) {
        // Authentication
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedinUser = userRepo.findByEmail(authenticatedUsername).orElseThrow();

        Item chosenItem = itemRepository.findById(itemId).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        Bookmark bookmark = bookmarkRepository.findById(new BookmarkId(loggedinUser.getId(), chosenItem.getId()))
                .orElseThrow(() -> {
                    return new ResponseStatusException(HttpStatus.OK); // If the bookmark doesn't exist, HTTP 200
                });

        bookmarkRepository.delete(bookmark);
    }

    /**
     * Checks if an item is bookmarked by the logged-in user
     *
     * @param token  jwt identifying the user
     * @param itemId the item id
     * @return the bookmark if it exists, else HTTP code 404
     */
    @GetMapping(path = "/{itemId}")
    public @ResponseBody boolean isBookmarked(@RequestHeader("Authorization") String token, @PathVariable int itemId) {
        // Authentication
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedinUser = userRepo.findByEmail(authenticatedUsername).orElseThrow();

        Item chosenItem = itemRepository.findById(itemId).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        return bookmarkRepository.findById(new BookmarkId(loggedinUser.getId(), chosenItem.getId())).isPresent();
    }

    /**
     * Gets all bookmarks from a logged-in user.
     *
     * @param token jwt identifying the user
     * @return all bookmarks by user id
     */
    @GetMapping(path = "")
    public @ResponseBody List<Bookmark> getAllBookmarksByUserId(@RequestHeader("Authorization") String token) {
        // Authentication
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedinUser = userRepo.findByEmail(authenticatedUsername).orElseThrow();
        int userId = loggedinUser.getId();

        List<Bookmark> result = bookmarkRepository.findByBookmarkIdUserId(userId);
        // returns HTTP status 200 whether user has bookmarks or not
        return result;
    }
}
