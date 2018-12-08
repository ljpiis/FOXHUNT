package xyz.gamification2018.foxhunt;

import android.Manifest;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CompoundButton.OnCheckedChangeListener {

    public Bitmap photo;
    public Boolean heatmapOn;
    private NavigationView navigationView;
    public Boolean scrollDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // request camera permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);

        heatmapOn = false;
        scrollDown = false;

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

        Switch s = (Switch) findViewById(R.id.switch_heatmap);
        if (s != null)
            s.setOnCheckedChangeListener(this);

        s = (Switch) findViewById(R.id.switch_night);
        if (s != null)
            s.setOnCheckedChangeListener(this);

        s = (Switch) findViewById(R.id.switch_sound);
        if (s != null)
            s.setOnCheckedChangeListener(this);

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
            displayFragment(R.id.nav_map);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // replaces the fragment based on the navigation item id,
    // pushes the old one to the back stack for back button function
    // and closes the navigation menu
    public void displayFragment(int id) {
        Fragment fragment = null;

        switch (id) {
            case R.id.nav_animal_identify:
                fragment = new AnimalIdentifyFragment();
                break;
            case R.id.nav_animal_info:
                fragment = new AnimalInfoFragment();
                break;
            case R.id.nav_animal_select:
                fragment = new AnimalSelectFragment();
                break;
            case R.id.nav_camera:
                fragment = new CameraFragment();
                break;
            case R.id.nav_info:
                fragment = new InfoFragment();
                break;
            case R.id.nav_leaderboard:
                fragment = new LeaderboardFragment();
                break;
            case R.id.nav_map:
                fragment = new MapFragment();
                break;
            case R.id.nav_profile:
                scrollDown = false;
                fragment = new ProfileFragment();
                break;
            case R.id.nav_registration:
                fragment = new RegistrationFragment();
                break;
            case R.id.nav_teams:
                fragment = new ProfileFragment();
                scrollDown = true;
                break;
            default:
                break;
        }

        // replace fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        // close menu
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    // every menu item that isn't a toggle switch is used to change fragment
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (item.getActionView() != null) {
            toggle(item);
        } else {
            displayFragment(id);
        }
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        Fragment f = null;
        switch (id) {
            case R.id.switch_heatmap:
                heatmapOn = isChecked;
                // redraw map
                f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                getSupportFragmentManager().beginTransaction().detach(f).attach(f).commit();
                break;
            case R.id.switch_night:
                if(isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                // hack for redrawing menus without recreating activity
                // (always takes you back to map screen for some reason?)
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                // redraw map
                f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                getSupportFragmentManager().beginTransaction().detach(f).attach(f).commit();
                break;
            case R.id.switch_sound:
                break;
            default:
                break;
        }
    }

    // for also toggling switch items when clicked outside the actual switch
    public void toggle(MenuItem item) {
        int id = item.getItemId();
        Switch s = null;
        switch (id) {
            case R.id.toggle_heatmap:
                s = (Switch)item.getActionView().findViewById(R.id.switch_heatmap);
                break;
            case R.id.toggle_night:
                s = (Switch)item.getActionView().findViewById(R.id.switch_night);
                break;
            case R.id.toggle_sounds:
                s = (Switch)item.getActionView().findViewById(R.id.switch_sound);
                break;
            default:
                break;
        }
        if (s != null)
            s.toggle();
    }

    // onClick functions for regular buttons
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

    public void openInfo(View view) {
        displayFragment(R.id.nav_info);
    }

    public void openLeaderboard(View view) {
        displayFragment(R.id.nav_leaderboard);
    }

    public void openMap(View view) {
        displayFragment(R.id.nav_map);
    }

    public void openProfile(View view) {
        displayFragment(R.id.nav_profile);
    }

    public void openRegistration(View view) {
        displayFragment(R.id.nav_registration);
    }

    public void openTeams(View view) {
        displayFragment(R.id.nav_teams);
    }

    public void scrollToCharacteristics(View view) {
        final ScrollView scrollView = findViewById(R.id.scrollAnimalInfo);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, findViewById(R.id.headerCharacteristics).getTop());
            }
        });
    }

    public void scrollToSightings(View view) {
        final ScrollView scrollView = findViewById(R.id.scrollAnimalInfo);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, findViewById(R.id.headerSightings).getTop());
            }
        });
    }
}
