package br.com.pilovieira.gt06.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import br.com.pilovieira.gt06.R;
import br.com.pilovieira.gt06.business.CommonOperations;
import br.com.pilovieira.gt06.databinding.ActivityMainBinding;
import br.com.pilovieira.gt06.location.LocationHistoryActivity;
import br.com.pilovieira.gt06.log.InfoFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private DrawerLayout drawer;

    private CommonOperations common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        drawer = binding.drawerLayout;

        common = new CommonOperations(getBaseContext());

        setSupportActionBar(binding.appBarMain.toolbar);

        configureDrawer();
        configureNavigationMenu();
        //requestPermissions();
        binding.appBarMain.contentMain.adView.loadAd(new AdRequest.Builder().build());

        binding.appBarMain.contentMain.btnHotGetLocation.setOnClickListener(this::locationAction);
        binding.appBarMain.contentMain.btnHotLock.setOnClickListener(view -> lockAction());
        binding.appBarMain.contentMain.btnHotUnlock.setOnClickListener(view -> unlockAction());
    }

    private void configureDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationMenu() {
        binding.navView.setNavigationItemSelectedListener(this);

        MenuItem item = binding.navView.getMenu().getItem(0);
        item.setChecked(true);
        onNavigationItemSelected(item);
    }

//    private void requestPermissions() {
//        String[] permissions = new String[] {
//                android.Manifest.permission.SEND_SMS,
//                Manifest.permission.CALL_PHONE,
//                Manifest.permission.ACCESS_FINE_LOCATION
//        };
//        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED)
//            ActivityCompat.requestPermissions(this, permissions, 1000);
//    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void locationAction(View view) {
        common.locationAction(view);
    }

    public void lockAction() {
        common.lockAction();
    }

    public void unlockAction() {
        common.unlockAction();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        selectItem(menuItem);
        drawer.closeDrawers();
        return true;
    }

    private void selectItem(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_info) {
            replaceFragment(new InfoFragment());
        } else if (itemId == R.id.nav_operations) {
            replaceFragment(new OperationsFragment());
        } else if (itemId == R.id.nav_advanced_operations) {
            replaceFragment(new AdvancedOperationsFragment());
        } else if (itemId == R.id.nav_configs) {
            replaceFragment(new ConfigsFragment());
        } else if (itemId == R.id.nav_parameters) {
            replaceFragment(new ParametersFragment());
//        } else if (itemId == R.id.nav_location_history) {
//            startActivity(new Intent(this, LocationHistoryActivity.class));
            return;
        }

        menuItem.setChecked(true);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_frame, fragment).commit();
    }

}
