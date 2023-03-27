package edu.ntnu.jakobkg.idatt2105projbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Integer id;
    @Setter
    @Getter
    private String title;
    @Setter
    @Getter
    private String description;
    @Setter
    @Getter
    private String date;
    @Setter
    @Getter
    private String latitude;
    @Setter
    @Getter
    private String longitude;
    @Setter
    @Getter
    private String location;
    @Setter
    @Getter
    private String price;
    @Setter
    @Getter
    private Integer categoryId;
    @Column(columnDefinition = "varchar(5000)")
    @Setter
    @Getter
    private String images;
    @Setter
    @Getter
    private Integer userId;

    public Item(String title, String description, String date, String latitude, String longitude, String location,
            String price, Integer categoryId, String images, Integer userId) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.price = price;
        this.categoryId = categoryId;
        this.images = images;
        this.userId = userId;
    }

    public Item() {
    }
}
