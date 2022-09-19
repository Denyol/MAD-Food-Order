package edu.curtin.danieltucker.foode;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private SQLiteDatabase database;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationBarView navBar = findViewById(R.id.navBar);

        database = new DBHelper(this).getWritableDatabase();

        fm = getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.mainFrameLayout, RestaurantsFragment.class, null). commit();

        navBar.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.basket:
                Log.d("MainActivity", "Switching to basket fragment");
                fm.beginTransaction().replace(R.id.mainFrameLayout, BasketFragment.class, null).commit();
                break;
            case R.id.restaurants:
                Log.d("MainActivity", "Switching to restaurants fragment");
                fm.beginTransaction().replace(R.id.mainFrameLayout, RestaurantsFragment.class, null). commit();
                break;
        }
        return true;
    }
}