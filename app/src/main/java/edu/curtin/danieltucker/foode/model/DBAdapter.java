package edu.curtin.danieltucker.foode.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import edu.curtin.danieltucker.foode.DBHelper;
import edu.curtin.danieltucker.foode.DatabaseSchema;

/**
 * Adapter to interface Restaurant and item objects from the Restaurant table in the database.
 */
public class DBAdapter {

    private final SQLiteDatabase db;

    public DBAdapter(SQLiteDatabase db) {
        this.db = db;
    }

    public DBAdapter(Context context) {
        this.db = new DBHelper(context).getWritableDatabase();
    }

    /**
     * Retrieve all Restaurants from the table.
     *
     * @return ArrayList containing Restaurant objects.
     */
    public ArrayList<Restaurant> getRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        Cursor c = db.query(DatabaseSchema.RestaurantTable.NAME, null, null,
                null, null, null, null);

        while (c.moveToNext()) {
            Restaurant r = getRestaurant(c);

            if (r != null) {
                restaurants.add(r);
            }
        }

        c.close();

        return restaurants;
    }

    /**
     * Get a Restaurant object from a cursor.
     *
     * @param cursor
     * @return Returns null if a Restaurant could not be made from the cursor at current position.
     */
    private Restaurant getRestaurant(Cursor cursor) {
        Restaurant result = null;
        try {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.RestaurantTable.Cols.NAME));
            int storeCode = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseSchema.RestaurantTable.Cols.ID));
            String banner = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.RestaurantTable.Cols.BANNER));

            result = new Restaurant(storeCode, name, banner);

        } catch (IllegalArgumentException e) {
            Log.e("DBAdapter", "", e);
        }

        Log.d("DBAdapter", "Got restaurant " + result.getName());

        return result;
    }

    public ArrayList<MenuItem> getItemsForRestaurant(Restaurant restaurant) {
        ArrayList<MenuItem> items = new ArrayList<>();
        Cursor c = db.query(DatabaseSchema.FoodTable.NAME, null,
                DatabaseSchema.FoodTable.Cols.RESTAURANT + "=" + restaurant.getStoreCode(),
                null, null, null, null);

        while (c.moveToNext()) {
            MenuItem i = getItem(c, restaurant);

            if (i != null)
                items.add(i);
        }

        c.close();

        return items;
    }

    private MenuItem getItem(Cursor cursor, Restaurant restaurant) {
        MenuItem result = null;
        try {
            int itemCode = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseSchema.FoodTable.Cols.ID));
            float price = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseSchema.FoodTable.Cols.PRICE));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.FoodTable.Cols.DESC));
            String resource = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.FoodTable.Cols.RESOURCE));

            result = new MenuItem(itemCode, price, desc, resource, restaurant);

        } catch (IllegalArgumentException e) {
            Log.e("DBAdapter", "", e);
        }

        return result;
    }

    public void insertRestaurant(Restaurant restaurant) {
        ContentValues restTableV = new ContentValues();
        restTableV.put(DatabaseSchema.RestaurantTable.Cols.ID, restaurant.getStoreCode());
        restTableV.put(DatabaseSchema.RestaurantTable.Cols.NAME, restaurant.getName());
        restTableV.put(DatabaseSchema.RestaurantTable.Cols.BANNER, restaurant.getBanner());

        long codeRest = db.insert(DatabaseSchema.RestaurantTable.NAME, null, restTableV);

        if (codeRest != -1) {
            Log.d("DBAdapter", String.format("Inserted restaurant %s at row %d.",
                    restaurant.getName(), codeRest));
        }
    }

    public void insertItem(MenuItem item) {
        ContentValues foodTableV = new ContentValues();
        foodTableV.put(DatabaseSchema.FoodTable.Cols.DESC, item.getDescription());
        foodTableV.put(DatabaseSchema.FoodTable.Cols.PRICE, String.format("%.2f", item.getPrice()));
        foodTableV.put(DatabaseSchema.FoodTable.Cols.RESOURCE, item.getResourceID());
        foodTableV.put(DatabaseSchema.FoodTable.Cols.RESTAURANT, item.getRestaurant().getStoreCode());

        long itemCode = db.insert(DatabaseSchema.FoodTable.NAME, null, foodTableV);

        if (itemCode != -1) {

            Log.d("DBAdapter", String.format("Inserted item %s at row %d.",
                    item.getDescription(), itemCode));
        }
    }
}
