package edu.curtin.danieltucker.foode;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DBHelper(this).getWritableDatabase();

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.mainFrameLayout, RestaurantsFragment.class, null). commit();
    }
}