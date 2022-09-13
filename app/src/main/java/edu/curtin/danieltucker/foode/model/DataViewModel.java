package edu.curtin.danieltucker.foode.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataViewModel extends ViewModel {

    private MutableLiveData<Restaurant> currentRestSelected;

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
