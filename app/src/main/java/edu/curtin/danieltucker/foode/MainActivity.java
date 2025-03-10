package edu.curtin.danieltucker.foode;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationBarView;

import edu.curtin.danieltucker.foode.model.DBHelper;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private SQLiteDatabase database;
    private FragmentManager fm;
    private NavigationBarView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navBar = findViewById(R.id.navBar);

        database = new DBHelper(getApplicationContext()).getWritableDatabase();

        fm = getSupportFragmentManager();

        int lastNav = -1;

        if (savedInstanceState != null) {
            lastNav = savedInstanceState.getInt("NAV_CURRENT", -1);
            switchFragment(lastNav);
            navBar.setSelectedItemId(lastNav);
        }

        // No saved instance state, set default
        if (lastNav == -1)
            switchFragment(R.id.navHome);

        navBar.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switchFragment(item.getItemId());
        return true;
    }

    /**
     * Replaces the fragment in the main frame layout
     * @param navMenuItemId resource ID of button in nav bar
     */
    private void switchFragment(int navMenuItemId) {
        switch (navMenuItemId) {
            case R.id.navHome:
                Log.d("MainActivity", "Switching to home fragment");

                // tabletFrame is null on non tablet layouts
                if (findViewById(R.id.tabletFrameLayout) != null) {
                    fm.beginTransaction()
                            .replace(R.id.mainFrameLayout, SpecialsMenuFragment.class, null)
                            .commit();
                    fm.beginTransaction()
                            .replace(R.id.tabletFrameLayout, RestaurantsFragment.class, null)
                            .commit();
                } else { // mobile layout
                    fm.beginTransaction()
                            .replace(R.id.mainFrameLayout, SpecialsMenuFragment.class, null)
                            .commit();
                }
                break;
            case R.id.basket:
                Log.d("MainActivity", "Switching to basket fragment");
                fm.beginTransaction().replace(R.id.mainFrameLayout, BasketFragment.class, null).commit();
                break;
            case R.id.restaurants:
                Log.d("MainActivity", "Switching to restaurants fragment");

                // tabletFrame is null on non tablet layouts
                if (findViewById(R.id.tabletFrameLayout) != null) {
                    fm.beginTransaction()
                            .replace(R.id.tabletFrameLayout, RestaurantsFragment.class, null)
                            .commit();
                } else { // mobile layout
                    fm.beginTransaction()
                            .replace(R.id.mainFrameLayout, RestaurantsFragment.class, null)
                            .commit();
                }
                break;
            case R.id.orders:
                Log.d("MainActivity", "Switching to order fragment");
                fm.beginTransaction().replace(R.id.mainFrameLayout, OrdersFragment.class, null). commit();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("NAV_CURRENT", navBar.getSelectedItemId());
        Log.d("MainActivity", "Saving state " + navBar.getSelectedItemId());
        super.onSaveInstanceState(outState);
    }
}