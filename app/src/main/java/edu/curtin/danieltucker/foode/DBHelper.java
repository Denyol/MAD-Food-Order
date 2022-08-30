package edu.curtin.danieltucker.foode;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "foode.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DatabaseSchema.RestaurantTable.NAME + "(" +
                DatabaseSchema.RestaurantTable.Cols.ID + " INTEGER PRIMARY KEY," +
                DatabaseSchema.RestaurantTable.Cols.NAME + " TEXT NOT NULL);");

        db.execSQL("CREATE TABLE " + DatabaseSchema.UsersTable.NAME + "(" +
                DatabaseSchema.UsersTable.Cols.ID + " INTEGER PRIMARY KEY," +
                DatabaseSchema.UsersTable.Cols.FIRST_NAME + " TEXT," +
                DatabaseSchema.UsersTable.Cols.LAST_NAME + " TEXT," +
                DatabaseSchema.UsersTable.Cols.EMAIL + " TEXT NOT NULL," +
                DatabaseSchema.UsersTable.Cols.PASSWORD + " TEXT NOT NULL);");

        db.execSQL("CREATE TABLE " + DatabaseSchema.ResourceTable.NAME + "(" +
                DatabaseSchema.ResourceTable.Cols.REF + " TEXT PRIMARY KEY," +
                DatabaseSchema.ResourceTable.Cols.RESTAURANT + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + DatabaseSchema.ResourceTable.Cols.RESTAURANT + ") REFERENCES " +
                DatabaseSchema.RestaurantTable.NAME + "(" + DatabaseSchema.RestaurantTable.Cols.ID + "));");

        db.execSQL("CREATE TABLE " + DatabaseSchema.FoodTable.NAME + "(" +
                DatabaseSchema.FoodTable.Cols.ID + " INTEGER PRIMARY KEY," +
                DatabaseSchema.FoodTable.Cols.RESTAURANT + " INTEGER NOT NULL," +
                DatabaseSchema.FoodTable.Cols.PRICE + " REAL NOT NULL," +
                DatabaseSchema.FoodTable.Cols.RESOURCE + " TEXT NOT NULL, '" +
                DatabaseSchema.FoodTable.Cols.DESC + "' TEXT, " +
                "FOREIGN KEY(" + DatabaseSchema.FoodTable.Cols.RESOURCE + ") REFERENCES " +
                DatabaseSchema.ResourceTable.NAME + "(" + DatabaseSchema.ResourceTable.Cols.REF + "), " +
                "FOREIGN KEY(" + DatabaseSchema.FoodTable.Cols.RESTAURANT + ") REFERENCES " +
                DatabaseSchema.RestaurantTable.NAME + "(" + DatabaseSchema.RestaurantTable.Cols.ID + "));");

        db.execSQL("CREATE TABLE " + DatabaseSchema.OrderTable.NAME + "(" +
                DatabaseSchema.OrderTable.Cols.ID + " INTEGER PRIMARY KEY," +
                DatabaseSchema.OrderTable.Cols.USER + " INTEGER NOT NULL," +
                DatabaseSchema.OrderTable.Cols.ITEM_CODE + " INTEGER NOT NULL," +
                DatabaseSchema.OrderTable.Cols.QUANTITY + " INTEGER DEFAULT 0," +
                "FOREIGN KEY(" + DatabaseSchema.OrderTable.Cols.ITEM_CODE + ") REFERENCES " +
                DatabaseSchema.FoodTable.NAME + "(" + DatabaseSchema.FoodTable.Cols.ID + "));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
