package edu.curtin.danieltucker.foode.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.curtin.danieltucker.foode.R;
import edu.curtin.danieltucker.foode.model.DatabaseSchema;
import edu.curtin.danieltucker.foode.model.MenuItem;
import edu.curtin.danieltucker.foode.model.Restaurant;
import edu.curtin.danieltucker.foode.model.DBAdapter;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 5;
    private static final String DB_NAME = "foode.db";
    private final Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DatabaseSchema.RestaurantTable.NAME + "(" +
                DatabaseSchema.RestaurantTable.Cols.ID + " INTEGER PRIMARY KEY," +
                DatabaseSchema.RestaurantTable.Cols.NAME + " TEXT NOT NULL," +
                DatabaseSchema.RestaurantTable.Cols.BANNER + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + DatabaseSchema.UsersTable.NAME + "(" +
                DatabaseSchema.UsersTable.Cols.ID + " INTEGER PRIMARY KEY," +
                DatabaseSchema.UsersTable.Cols.EMAIL + " TEXT UNIQUE NOT NULL," +
                DatabaseSchema.UsersTable.Cols.PASSWORD + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + DatabaseSchema.FoodTable.NAME + "(" +
                DatabaseSchema.FoodTable.Cols.ID + " INTEGER PRIMARY KEY," +
                DatabaseSchema.FoodTable.Cols.RESTAURANT + " INTEGER NOT NULL," +
                DatabaseSchema.FoodTable.Cols.PRICE + " REAL NOT NULL," +
                DatabaseSchema.FoodTable.Cols.RESOURCE + " TEXT NOT NULL, '" +
                DatabaseSchema.FoodTable.Cols.DESC + "' TEXT," +
                "FOREIGN KEY(" + DatabaseSchema.FoodTable.Cols.RESTAURANT + ") REFERENCES " +
                DatabaseSchema.RestaurantTable.NAME + "(" + DatabaseSchema.RestaurantTable.Cols.ID + "))");

        db.execSQL("CREATE TABLE " + DatabaseSchema.OrderTable.NAME + "(" +
                DatabaseSchema.OrderTable.Cols.ID + " INTEGER NOT NULL," +
                DatabaseSchema.OrderTable.Cols.USER + " INTEGER NOT NULL," +
                DatabaseSchema.OrderTable.Cols.ITEM_CODE + " INTEGER NOT NULL," +
                DatabaseSchema.OrderTable.Cols.QUANTITY + " INTEGER DEFAULT 0," +
                "PRIMARY KEY(" + DatabaseSchema.OrderTable.Cols.ID + "," +
                DatabaseSchema.OrderTable.Cols.ITEM_CODE + "," +
                DatabaseSchema.OrderTable.Cols.USER + ")," +
                "FOREIGN KEY(" + DatabaseSchema.OrderTable.Cols.ITEM_CODE + ") REFERENCES " +
                DatabaseSchema.FoodTable.NAME + "(" + DatabaseSchema.FoodTable.Cols.ID + "))");

        initDatabaseData(db);
    }

    /**
     * Initialises the Restaurant, Resource, and Food tables with restaurant data from menu.json.
     * @param db
     */
    private void initDatabaseData(SQLiteDatabase db) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.menu);
        StringBuilder json = new StringBuilder();

        DBAdapter databaseAdapter = new DBAdapter(db);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            while (line != null) { // Read in file to string
                json.append(line).append("\n");
                line = reader.readLine();
            }

            reader.close();
            inputStream.close();

            JSONObject jsonObject = new JSONObject(json.toString());

            JSONArray restArray = jsonObject.getJSONArray("restaurants");
            Log.d("Database Init", json.toString());

            for(int i = 0; i < restArray.length(); i++) {
                JSONObject obj = restArray.getJSONObject(i);

                String name = obj.getString("name");
                String banner = obj.getString("banner");
                Restaurant r = new Restaurant(i, name, banner);

                databaseAdapter.insertRestaurant(r);

                JSONArray items = obj.optJSONArray("items");

                if (items != null) {
                    // Enumerate all items per restaurant
                    for (int j = 0; j < items.length(); j++) {
                        JSONObject itemJSON = items.getJSONObject(j);

                        String desc = itemJSON.getString("desc");
                        String resource = itemJSON.getString("res");
                        double price = itemJSON.getDouble("price");

                        MenuItem item = new MenuItem(0, (float) price, desc, resource, r);
                        databaseAdapter.insertItem(item);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            Log.e("DBHelper", "initDatabaseData: ", e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop all tables, rebuild

        db.execSQL("DROP TABLE " + DatabaseSchema.OrderTable.NAME);
        db.execSQL("DROP TABLE " + DatabaseSchema.FoodTable.NAME);
        db.execSQL("DROP TABLE " + DatabaseSchema.RestaurantTable.NAME);
        db.execSQL("DROP TABLE " + DatabaseSchema.UsersTable.NAME);

        onCreate(db);
    }
}
