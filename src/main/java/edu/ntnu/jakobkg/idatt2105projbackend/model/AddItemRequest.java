package edu.ntnu.jakobkg.idatt2105projbackend.model;

public record AddItemRequest(
    String title,
    String description,
    String date,
    String latitude,
    String longitude,
    String price,
    int categoryId,
    String images) {}