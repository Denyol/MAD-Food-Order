package edu.curtin.danieltucker.foode.model;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class MenuItem implements Serializable {
    private final int itemCode;
    private final float price;
    private final String description;
    private final String resourceID;
    private final Restaurant restaurant;

    public MenuItem(int itemCode, float price, String description, String resourceID, Restaurant restaurant) {
        this.itemCode = itemCode;
        this.price = price;
        this.description = description;
        this.resourceID = resourceID;
        this.restaurant = restaurant;
    }

    public String getDescription() {
        return description;
    }

    public int getItemCode() {
        return itemCode;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public float getPrice() {
        return price;
    }

    public String getResourceID() {
        return resourceID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode, price, description, resourceID, restaurant);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj == this) {
            result = true;
        } else if (obj != null && obj.getClass() == this.getClass()) {
            MenuItem o = (MenuItem) obj;
            result = o.hashCode() == this.hashCode();
        }

        return result;
    }
}
