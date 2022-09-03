package edu.curtin.danieltucker.foode.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import edu.curtin.danieltucker.foode.DBHelper;
import edu.curtin.danieltucker.foode.DatabaseSchema;

public class RestaurantAdapter {

    private SQLiteDatabase db;

    public RestaurantAdapter(SQLiteDatabase db) {
        this.db = db;
    }

    public RestaurantAdapter(Context context) {
        this.db = new DBHelper(context).getWritableDatabase();
    }

    public ArrayList<Restaurant> getRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseSchema.RestaurantTable.NAME + " INNER JOIN " +
                        DatabaseSchema.ResourceTable.NAME + " ON " +
                        DatabaseSchema.RestaurantTable.Cols.ID + " = " +
                        DatabaseSchema.ResourceTable.Cols.RESTAURANT,
                null);
        c.moveToFirst();

        do {
            Restaurant r = getRestaurant(c);

            if (r != null) {
                restaurants.add(r);
            }
        }
        while(c.moveToNext());

        c.close();

        return restaurants;
    }

    /**
     * Get a Restaurant object from a cursor.
     * @param cursor
     * @return Returns null if a Restaurant could not be made from the cursor at current position.
     */
    private Restaurant getRestaurant(Cursor cursor) {
        Restaurant result = null;
        try {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.RestaurantTable.Cols.NAME));
            int storeCode = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseSchema.RestaurantTable.Cols.ID));
            String res = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.ResourceTable.Cols.REF));

            result = new Restaurant(storeCode, name, res);

        } catch (IllegalArgumentException e) {
            Log.e("RestaurantAdapter", "", e);
        }

        Log.d("RestaurantAdapter", "Got restaurant " + result.getName());

        return result;
    }

    public void insertRestaurant(Restaurant restaurant) {
        ContentValues restTableV = new ContentValues();
        restTableV.put(DatabaseSchema.RestaurantTable.Cols.ID, restaurant.getStoreCode());
        restTableV.put(DatabaseSchema.RestaurantTable.Cols.NAME, restaurant.getName());

        long codeRest = db.insert(DatabaseSchema.RestaurantTable.NAME, null, restTableV);

        if (codeRest != -1) {
            ContentValues resTableV = new ContentValues();
            resTableV.put(DatabaseSchema.ResourceTable.Cols.REF, restaurant.getResourceId());
            resTableV.put(DatabaseSchema.ResourceTable.Cols.RESTAURANT, (int) codeRest);
            long codeRes = db.insert(DatabaseSchema.ResourceTable.NAME, null, resTableV);

            Log.d("RestaurantAdapter", String.format("Inserted restaurant %s at row %d.", restaurant.getName(), codeRes));
        }

    }

}
