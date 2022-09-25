package edu.curtin.danieltucker.foode.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Restaurant> getRestaurants() {
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

    public List<Order> getOrders(int userId) {
        HashMap<Integer, Order> orders = new HashMap<>();

        String selection = null;

        if (userId != -1)
            selection = DatabaseSchema.OrderTable.Cols.USER + "=" + userId;
        else
            throw new IllegalArgumentException("-1 is not a valid user id");

        Cursor c = db.query(DatabaseSchema.OrderTable.NAME, null, selection,
                null, null, null, null);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while (c.moveToNext()) {
            int orderId = c.getInt(0);
            int itemCode = c.getInt(2);
            int quantity = c.getInt(3);
            String dateString = c.getString(4);
            Date date = null;

            try {
                date = sdf.parse(dateString);
            } catch (ParseException e) {
                Log.w("DBAdapter", e);
            }

            MenuItem item = getItem(itemCode);

            if (!orders.containsKey(orderId)) {
                Order o = new Order(orderId, item.getRestaurant(), userId, date);
                orders.put(orderId, o);
            }

            orders.get(orderId).addItem(item, quantity);
        }

        c.close();

        return new ArrayList<>(orders.values());
    }

    /**
     * Get a Restaurant object from a cursor.
     *
     * @param cursor
     * @return Returns null if a Restaurant could not be made from the cursor at current position.
     */
    @Nullable
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

    private Restaurant getRestaurant(int restId) {
        Cursor c = db.query(DatabaseSchema.RestaurantTable.NAME, null,
                DatabaseSchema.RestaurantTable.Cols.ID + "=" + restId,
                null, null, null, null);

        if (!c.moveToFirst()) {
            c.close();
            return null;
        }

        return getRestaurant(c);
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

    /**
     * @return ArrayList of menu items random 10 from database
     */
    public ArrayList<MenuItem> getSpecialsItems() {
        ArrayList<MenuItem> items = new ArrayList<>();
        Cursor c = db.query(DatabaseSchema.FoodTable.NAME, null, null,
                null, null, null, null, "10");

        while (c.moveToNext()) {
            int restId = c.getInt(1);
            Restaurant restaurant = getRestaurant(restId);

            MenuItem i = getItem(c, restaurant);

            if (i != null)
                items.add(i);
        }

        c.close();

        return items;
    }

    public MenuItem getItem(int itemCode) {
        Cursor c = db.query(DatabaseSchema.FoodTable.NAME, null,
                DatabaseSchema.FoodTable.Cols.ID + "=" +itemCode,
                null, null, null, null);

        int restCode = -1;

        if (c.moveToFirst())
            restCode = c.getInt(1);
        else {
            c.close();
            return null;
        }

        MenuItem i = getItem(c, getRestaurant(restCode));
        c.close();

        return i;
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

    /**
     * @param email    email
     * @param password password
     * @return user ID or -1 if none found.
     */
    public int login(String email, String password) {
        String selection =  String.format("%s='%s' AND %s='%s'", DatabaseSchema.UsersTable.Cols.EMAIL,
                email, DatabaseSchema.UsersTable.Cols.PASSWORD, password);

        Cursor c = db.query(DatabaseSchema.UsersTable.NAME, new String[]{"userId"},
                selection, null, null, null, null);

        int result = -1;

        if (c.moveToFirst())
            result = c.getInt(0);

        c.close();

        return result;
    }

    /**
     * Returns -1 if user already exists or user Id of new user.
     * @param email
     * @param password
     */
    public int addUser(String email, String password) {
        // See if email exists
        String selection =  String.format("%s='%s'", DatabaseSchema.UsersTable.Cols.EMAIL, email);

        Cursor c = db.query(DatabaseSchema.UsersTable.NAME, new String[]{"userId"},
                selection, null, null, null, null);

        long result = -1;

        if (!c.moveToFirst()) { // If no row exists i.e. no email
            ContentValues v = new ContentValues();
            v.put(DatabaseSchema.UsersTable.Cols.EMAIL, email);
            v.put(DatabaseSchema.UsersTable.Cols.PASSWORD, password);

            result = db.insert(DatabaseSchema.UsersTable.NAME, null, v);
        }

        c.close();

        return (int) result;
    }

    public void addOrder(int userId, Map<MenuItem, Integer> items) {
        int orderID = items.hashCode();
        for (MenuItem item : items.keySet()) {
            addOrder(orderID, userId, item, items.get(item));
        }
    }

    public void addOrder(int orderID, int userID, MenuItem item, Integer count) {
        ContentValues v = new ContentValues();
        v.put(DatabaseSchema.OrderTable.Cols.ID, orderID);
        v.put(DatabaseSchema.OrderTable.Cols.USER, userID);
        v.put(DatabaseSchema.OrderTable.Cols.QUANTITY, count);
        v.put(DatabaseSchema.OrderTable.Cols.ITEM_CODE, item.getItemCode());

        long res = db.insert(DatabaseSchema.OrderTable.NAME , "", v);
        Log.d("DBAdapter", "addOrder item res " + res);
    }
}
