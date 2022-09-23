package edu.curtin.danieltucker.foode.model;

import java.util.HashMap;
import java.util.Map;

public class Order {

    private final Restaurant restaurant;
    private final int id;
    private final int userId;
    private final Map<MenuItem, Integer> items;

    public Order(int id, Restaurant restaurant, int userId) {
        this.restaurant = restaurant;
        this.id = id;
        this.userId = userId;
        this.items = new HashMap<>();
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public int size() {
        return items.size();
    }

    public void addItem(MenuItem item, int count) {
        items.put(item, count);
    }

    public float value() {
        float result = 0;

        for (MenuItem item : items.keySet()) {
            result += item.getPrice() * items.get(item);
        }

        return result;
    }
}
