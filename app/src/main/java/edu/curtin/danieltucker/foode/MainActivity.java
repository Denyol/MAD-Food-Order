package edu.curtin.danieltucker.foode;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONException;

import edu.curtin.danieltucker.foode.model.FoodeData;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DBHelper(this).getWritableDatabase();
        FoodeData d = new FoodeData(this);
        d.initDatabaseData();

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.mainFrameLayout, RestaurantsFragment.class, null). commit();
    }
}