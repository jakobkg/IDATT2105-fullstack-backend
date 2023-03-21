package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Bookmark;
import edu.ntnu.jakobkg.idatt2105projbackend.model.BookmarkId;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.BookmarkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {
    Logger logger = LoggerFactory.getLogger(BookmarkController.class);

    @Autowired
    BookmarkRepository bookmarkRepository;

    //todo: fjern kommentering når ItemRepository finnes
    //@Autowired
    //ItemRepository itemRepository;

    @PostMapping(path = "/{itemId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Bookmark addBookmark(@PathVariable int itemId) {
        //todo: her henter vi userId via jwt token
        int userId = 1;
        //todo: fjern kommentering når ItemRepository finnes
/*        if (!itemRepository.existsById(itemId)) {
            logger.warn("Item with itemId: " + itemId + " does not exist");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }*/
        if (!bookmarkRepository.existsById(new BookmarkId(userId, itemId)))
            return bookmarkRepository.save(new Bookmark(new BookmarkId(userId, itemId)));

        return bookmarkRepository.findById(new BookmarkId(userId, itemId)).orElseThrow(); // if this runs, bookmark already exists
    }

    @DeleteMapping(path = "/{itemId}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody void deleteBookmark(@PathVariable int itemId) {
        //todo: her henter vi userId via jwt token
        int userId = 1;

        // HTTP status 200 if there is nothing to delete
        if (!bookmarkRepository.existsById(new BookmarkId(userId, itemId))) return;

        bookmarkRepository.delete(new Bookmark(new BookmarkId(userId, itemId)));
    }

    @GetMapping(path = "/{itemId}")
    public @ResponseBody Bookmark isBookmarked(@PathVariable int itemId) {
        //todo: her henter vi userId via jwt token
        int userId = 1;
        Bookmark bookmark = bookmarkRepository.findById(new BookmarkId(userId, itemId)).orElseThrow(() -> {
            logger.warn("Bookmark with userId: " + userId + " and itemId: " + itemId + " not found.");
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        logger.info("Bookmark with userId: " + userId + " and itemId: " + itemId + " found.");
        return bookmark;
    }


    public @ResponseBody List<Bookmark> getAllBookmarksByUserId() {
        //todo: her henter vi userId via jwt token
        int userId = 1;
        List<Bookmark> result = bookmarkRepository.findByBookmarkIdUserId(userId);
        // returns HTTP status 200 if user has no bookmarks
        if (result.isEmpty()) throw new ResponseStatusException(HttpStatus.OK);

        return result;
    }

}
