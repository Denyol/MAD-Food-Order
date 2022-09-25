package edu.curtin.danieltucker.foode.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Restaurant implements Serializable {

    private final int storeCode;
    private final String name;
    private final String banner;

    public Restaurant(int storeCode, @NonNull String name, @NonNull String banner) {
        this.storeCode = storeCode;
        this.name = name;
        this.banner = banner;
    }

    public int getStoreCode() {
        return storeCode;
    }

    public String getName() {
        return name;
    }

    public String getBanner() {
        return banner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return storeCode == that.storeCode && name.equals(that.name) && banner.equals(that.banner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeCode, name, banner);
    }
}
