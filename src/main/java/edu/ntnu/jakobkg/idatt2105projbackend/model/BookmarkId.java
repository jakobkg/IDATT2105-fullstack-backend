package edu.ntnu.jakobkg.idatt2105projbackend.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BookmarkId implements Serializable {

    private Integer userId;

    private Integer itemId;

    public BookmarkId (int userId, int itemId) {
        // Assumes ID's start at 1
        if (userId != 0 && itemId != 0) {
            this.userId = userId;
            this.itemId = itemId;
        }
    }

    public BookmarkId() {

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
