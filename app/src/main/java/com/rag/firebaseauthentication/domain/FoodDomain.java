package com.rag.firebaseauthentication.domain;

public class FoodDomain {

    private String title;
    private String price;
    private String description;
    private Integer preparationTime;
    private Integer calories;
    private FastFoodCategory fastFoodCategory;

    public FoodDomain() {
    }

    public FoodDomain(String title
            , String price
            , String description
            , Integer preparationTime
            , int calories
            , FastFoodCategory fastFoodCategory) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.preparationTime = preparationTime;
        this.calories = calories;
        this.fastFoodCategory = fastFoodCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public FastFoodCategory getFastFoodCategory() {
        return fastFoodCategory;
    }

    public void setFastFoodCategory(FastFoodCategory fastFoodCategory) {
        this.fastFoodCategory = fastFoodCategory;
    }
}
