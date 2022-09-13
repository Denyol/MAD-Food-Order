package edu.curtin.danieltucker.foode.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class BasketViewModel extends ViewModel {

    private MutableLiveData<HashMap<MenuItem, Integer>> basket;
    private MutableLiveData<Restaurant> restaurant;

    public LiveData<HashMap<MenuItem, Integer>> getBasket() {
        if (basket == null) {
            basket = new MutableLiveData<>(new HashMap<>());
        }

        return basket;
    }

    /**
     * Causes the basket LiveData to notify observers of change.
     */
    public void notifyBasketChanged() {
        this.basket.setValue(this.basket.getValue());
    }

    public LiveData<Restaurant> getRestaurant() {
        if (restaurant == null)
            restaurant = new MutableLiveData<>();

        return restaurant;
    }

    /**
     * Sets the current basket to reflect items from a specific restaurant.
     * Clears basket items if the current restaurant is different to the param.
     * @param restaurant
     */
    public void setRestaurant(@NonNull Restaurant restaurant) {
        if (getRestaurant().getValue() != restaurant) {
            this.restaurant.setValue(restaurant);

            getBasket().getValue().clear();
        }
    }

}
