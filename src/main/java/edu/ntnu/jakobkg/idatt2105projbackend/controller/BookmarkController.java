package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import edu.ntnu.jakobkg.idatt2105projbackend.repo.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bookmark")
public class BookmarkController {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @PostMapping(path = "")
    public @ResponseBody ResponseEntity addBookmark(@RequestParam int userId, @RequestParam int itemId) {


        return new ResponseEntity(HttpStatus.CREATED);
    }



}
