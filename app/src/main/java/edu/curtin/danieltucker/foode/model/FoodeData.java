package edu.curtin.danieltucker.foode.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.JsonReader;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.curtin.danieltucker.foode.DBHelper;
import edu.curtin.danieltucker.foode.DatabaseSchema;
import edu.curtin.danieltucker.foode.R;

public class FoodeData extends ViewModel {

    private final SQLiteDatabase db;
    private final Context context;
    private final RestaurantAdapter restaurantAdapter;

    public FoodeData(Context context) {
        db = new DBHelper(context).getWritableDatabase();
        this.context = context;
        restaurantAdapter = new RestaurantAdapter(db);
    }

    public void initDatabaseData() {
        InputStream inputStream = context.getResources().openRawResource(R.raw.menu);
        StringBuilder json = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            while (line != null) {
                json.append(line).append("\n");
                line = reader.readLine();
            }

            reader.close();
            inputStream.close();

            JSONObject jsonObject = new JSONObject(json.toString());

            JSONArray restArray = jsonObject.getJSONArray("restaurants");
            Log.d("Database", json.toString());

            for(int i = 0; i < restArray.length(); i++) {
                JSONObject obj = restArray.getJSONObject(i);

                String name = obj.getString("name");
                String resource = obj.getString("banner");
                Restaurant r = new Restaurant(i, name, resource);

                restaurantAdapter.insertRestaurant(r);
            }
        } catch (IOException | JSONException e) {
            Log.e("FoodeData", "initDatabaseData: ", e);
        }

    }


}
