package edu.ntnu.jakobkg.idatt2105projbackend.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A unique ID describing a Bookmark, it includes the item ID and the user ID
 */
@Embeddable
public class BookmarkId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "userId")
    @Getter
    @Setter
    private User user;

    @ManyToOne
    @JoinColumn(name = "itemId")
    @Getter
    @Setter
    private Item item;

    /**
     * Instantiates a new Bookmark id.
     *
     * @param userId the user id
     * @param itemId the item id
     */
    public BookmarkId (User user, Item item) {
        this.item = item;
        this.user = user;
    }

    /**
     * Instantiates a new Bookmark id without any assigned id's.
     */
    public BookmarkId() {

    }
}
