package edu.curtin.danieltucker.foode.model;

public class Restaurant {

    private final int storeCode;
    private final String name;
    private final String resourceId;

    public Restaurant(int storeCode, String name, String resourceId) {
        this.storeCode = storeCode;
        this.name = name;
        this.resourceId = resourceId;
    }

    public int getStoreCode() {
        return storeCode;
    }

    public String getName() {
        return name;
    }

    public String getResourceId() {
        return resourceId;
    }
}
