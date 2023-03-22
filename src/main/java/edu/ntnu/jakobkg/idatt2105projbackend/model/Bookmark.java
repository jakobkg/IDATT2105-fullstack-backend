package edu.ntnu.jakobkg.idatt2105projbackend.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

/**
 * Representing a bookmark for a user. A bookmark is includes the user's userId as well as the item's itemId that is
 * bookmarked.
 */
@Entity
public class Bookmark {

    // Contains userId and itemId
    @EmbeddedId
    private BookmarkId bookmarkId;

    /**
     * Instantiates a new Bookmark.
     *
     * @param bookmarkId the bookmark id containing a userId and an itemId
     */
    public Bookmark(BookmarkId bookmarkId) {
        this.bookmarkId = bookmarkId;
    }


    public Bookmark() {

    }

    /**
     * Gets bookmark id.
     *
     * @return the bookmark id
     */
    public BookmarkId getBookmarkId() {
        return bookmarkId;
    }

    /**
     * Sets bookmark id.
     *
     * @param bookmarkId the bookmark id
     */
    public void setBookmarkId(BookmarkId bookmarkId) {
        this.bookmarkId = bookmarkId;
    }
}
