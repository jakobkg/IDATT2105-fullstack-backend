package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Bookmark;
import edu.ntnu.jakobkg.idatt2105projbackend.model.BookmarkId;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.BookmarkRepository;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    Logger logger = LoggerFactory.getLogger(BookmarkController.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    BookmarkRepository bookmarkRepository;

    //todo: fjern kommentering når ItemRepository finnes
    //@Autowired
    //ItemRepository itemRepository;

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
        int userId = loggedinUser.getId();

        //todo: fjern kommentering når ItemRepository finnes
/*        if (!itemRepository.existsById(itemId)) {
            logger.warn("Item with itemId: " + itemId + " does not exist");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }*/
        if (!bookmarkRepository.existsById(new BookmarkId(userId, itemId)))
            return bookmarkRepository.save(new Bookmark(new BookmarkId(userId, itemId)));

        return bookmarkRepository.findById(new BookmarkId(userId, itemId)).orElseThrow(); // if this runs, bookmark already exists
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
        int userId = loggedinUser.getId();

        // HTTP status 200 if there is nothing to delete
        if (!bookmarkRepository.existsById(new BookmarkId(userId, itemId))) return;

        bookmarkRepository.delete(new Bookmark(new BookmarkId(userId, itemId)));
    }

    /**
     * Checks if an item is bookmarked by the logged-in user
     *
     * @param token  jwt identifying the user
     * @param itemId the item id
     * @return the bookmark if it exists, else HTTP code 404
     */
    @GetMapping(path = "/{itemId}")
    public @ResponseBody Bookmark isBookmarked(@RequestHeader("Authorization") String token, @PathVariable int itemId) {
        // Authentication
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedinUser = userRepo.findByEmail(authenticatedUsername).orElseThrow();
        int userId = loggedinUser.getId();

        Bookmark bookmark = bookmarkRepository.findById(new BookmarkId(userId, itemId)).orElseThrow(() -> {
            logger.warn("Bookmark with userId: " + userId + " and itemId: " + itemId + " not found.");
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        logger.info("Bookmark with userId: " + userId + " and itemId: " + itemId + " found.");
        return bookmark;
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
