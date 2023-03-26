package edu.ntnu.jakobkg.idatt2105projbackend.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A unique ID describing a Bookmark, it includes the item ID and the user ID
 */
@Embeddable
public class BookmarkId implements Serializable {

    
    @Getter
    @Setter
    private Integer userId;

    @Getter
    @Setter
    private Integer itemId;

    /**
     * Instantiates a new Bookmark id.
     *
     * @param userId the user id
     * @param itemId the item id
     */
    public BookmarkId (Integer userID, Integer itemID) {
        this.itemId = itemID;
        this.userId = userID;
    }

    /**
     * Instantiates a new Bookmark id without any assigned id's.
     */
    public BookmarkId() {

    }
}
