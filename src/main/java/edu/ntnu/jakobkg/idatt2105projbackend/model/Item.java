package edu.ntnu.jakobkg.idatt2105projbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String description;
    //decide if date should be string or something else like this: - @Column(columnDefinition="varchar2(20)")
    private String date;
    private String latitude;
    private String longitude;
    private String price;
    private int categoryId;
    private String images;
    private int userId;

    public Item(String title, String description, String date, String latitude, String longitude, String price, int categoryId, String images, int userId) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.categoryId = categoryId;
        this.images = images;
        this.userId = userId;
    }


    public Item() {

    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Id: "+this.id+" "+" Title: "+this.title+" Description: "+this.description+
                " Date: "+this.date+" Latitude: "+this.latitude+" Longitude: "+this.longitude+
                " Price: "+this.price+" CategoryId: "+ this.categoryId+" Images: "+this.images+
                " UserId: "+this.userId;
    }
}
