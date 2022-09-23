package edu.curtin.danieltucker.foode.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class BasketViewModel extends ViewModel {

    private MutableLiveData<HashMap<MenuItem, Integer>> basket;
    private MutableLiveData<Restaurant> restaurant;

    public BasketViewModel() {
        basket = new MutableLiveData<>(new HashMap<>());
        restaurant = new MutableLiveData<>();
    }

    public HashMap<MenuItem, Integer> getBasket() {
        return basket.getValue();
    }

    /**
     * @return Restaurant name or null if no restaurant in basket.
     */
    @Nullable
    public String getRestaurantName() {
        return getRestaurant() == null ? null : getRestaurantData().getValue().getName();
    }

    public float getTotal() {
        HashMap<MenuItem, Integer> b = getBasket();
        float result = 0;

        for (MenuItem item : b.keySet()) {
            result += item.getPrice() * b.get(item);
        }

        return result;
    }

    /**
     * Causes the basket LiveData to notify observers of change.
     */
    private void notifyBasketChanged() {
        basket.setValue(this.basket.getValue());
    }

    public void putItem(MenuItem item, int count) {
        if (restaurant.getValue() == null
                || !!getRestaurantName().equals(item.getRestaurant().getName()))
            setRestaurant(item.getRestaurant());

        getBasket().put(item, count);
        notifyBasketChanged();
    }

    public void removeItem(MenuItem item) {
        getBasket().remove(item);
        notifyBasketChanged();

        if (getBasket().size() == 0)
            restaurant.setValue(null);
    }

    public LiveData<Restaurant> getRestaurantData() {
        return restaurant;
    }

    public LiveData<HashMap<MenuItem, Integer>> getBasketData() {
        return basket;
    }

    @Nullable
    public Restaurant getRestaurant() {
        return restaurant.getValue();
    }

    /**
     * Sets the current basket to reflect items from a specific restaurant.
     * Clears basket items if the current restaurant is different to the param.
     * @param restaurant
     */
    private void setRestaurant(@NonNull Restaurant restaurant) {
        if (this.restaurant.getValue() != restaurant) {
            this.restaurant.setValue(restaurant);

            getBasket().clear();
        }
    }

}
