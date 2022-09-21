package edu.curtin.danieltucker.foode.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataViewModel extends ViewModel {

    private MutableLiveData<Restaurant> currentRestSelected;
    private MutableLiveData<Integer> currentUser;

    public int getCurrentUser() {
        if (currentUser == null)
            currentUser = new MutableLiveData<>(-1);

        return currentUser.getValue();
    }

    public void setCurrentUser(int userID) {
        if (currentUser == null)
            currentUser = new MutableLiveData<>(userID);
        else
            currentUser.setValue(userID);
    }

    public Restaurant getCurrentRestSelected() {
        if (currentRestSelected == null)
            currentRestSelected = new MutableLiveData<>();

        return currentRestSelected.getValue();
    }

    public void setCurrentRestSelected(Restaurant newSelected) {
        this.getCurrentRestSelected();
        this.currentRestSelected.setValue(newSelected);
    }
}
