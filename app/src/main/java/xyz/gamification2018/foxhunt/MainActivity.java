package xyz.gamification2018.foxhunt;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displayFragment(R.id.nav_map);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_instant_mode) {
            displayFragment(R.id.nav_camera);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayFragment(int id) {
        Fragment fragment = null;

        switch (id) {
            case R.id.nav_camera:
                fragment = new CameraFragment();
                break;
            case R.id.nav_map:
                fragment = new MapFragment();
                break;
            case R.id.nav_animal_select:
                fragment = new AnimalSelectFragment();
                break;
            case R.id.nav_animal_info:
                fragment = new AnimalInfoFragment();
                break;
            case R.id.nav_animal_identify:
                fragment = new AnimalIdentifyFragment();
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
            default:
                break;
        }

        // replace the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        displayFragment(item.getItemId());
        return true;
    }

    public void openAnimalIdentify(View view) {
        displayFragment(R.id.nav_animal_identify);
    }

    public void openAnimalInfo(View view) {
        displayFragment(R.id.nav_animal_info);
    }

    public void openAnimalSelect(View view) {
        displayFragment(R.id.nav_animal_select);
    }

    public void openCamera(View view) {
        displayFragment(R.id.nav_camera);
    }

    public void openMap(View view) {
        displayFragment(R.id.nav_map);
    }

    public void openSettings(View view) {
        displayFragment(R.id.nav_settings);
    }
}