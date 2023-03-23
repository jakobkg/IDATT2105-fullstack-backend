package edu.ntnu.jakobkg.idatt2105projbackend.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer.UserInfoEndpointConfig;

/**
 * A unique ID describing a Bookmark, it includes the item ID and the user ID
 */
@Embeddable
public class BookmarkId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;

    /**
     * Instantiates a new Bookmark id.
     *
     * @param userId the user id
     * @param itemId the item id
     */
    public BookmarkId (User user, Item item) {
        // Assumes ID's start at 1
        if (userId != 0 && itemId != 0) {
            this.userId = userId;
            this.itemId = itemId;
        }
    }

    /**
     * Instantiates a new Bookmark id without any assigned id's.
     */
    public BookmarkId() {

    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * Sets item id.
     *
     * @param itemId the item id
     */
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
