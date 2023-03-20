package edu.ntnu.jakobkg.idatt2105projbackend.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Bookmark {

    // Contains userId and itemId
    @EmbeddedId
    private BookmarkId bookmarkId;

    public Bookmark(BookmarkId bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public Bookmark() {

    }

    public BookmarkId getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(BookmarkId bookmarkId) {
        this.bookmarkId = bookmarkId;
    }
}
