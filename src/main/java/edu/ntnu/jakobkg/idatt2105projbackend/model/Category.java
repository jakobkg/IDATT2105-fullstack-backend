package edu.ntnu.jakobkg.idatt2105projbackend.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String categoryName;

    public Category() {

    }

    Category(String categoryName) {
        this.categoryName = categoryName;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
