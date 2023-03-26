package edu.ntnu.jakobkg.idatt2105projbackend.model;

public record AddItemRequest(
    String title,
    String description,
    String latitude,
    String longitude,
    String location,
    String price,
    int categoryId,
    String images) {}