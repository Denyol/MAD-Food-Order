package edu.curtin.danieltucker.foode.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {

    private final Restaurant restaurant;
    private final int id;
    private final int userId;
    private final Map<MenuItem, Integer> items;
    private final Date date;

    public Order(int id, Restaurant restaurant, int userId, Date date) {
        this.restaurant = restaurant;
        this.id = id;
        this.userId = userId;
        this.items = new HashMap<>();
        this.date = date;
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

    public Map<MenuItem, Integer> getItems() {
        return items;
    }

    public float getTotal() {
        float result = 0f;

        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            result += entry.getValue() * entry.getKey().getPrice();
        }

        return result;
    }

    public Date getDate() {
        return date;
    }
}
