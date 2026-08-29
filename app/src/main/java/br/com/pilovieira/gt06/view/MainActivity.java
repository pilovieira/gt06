package br.com.pilovieira.gt06.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.material.navigation.NavigationBarView;

import br.com.pilovieira.gt06.R;
import br.com.pilovieira.gt06.business.CommonOperations;
import br.com.pilovieira.gt06.databinding.ActivityMainBinding;
import br.com.pilovieira.gt06.log.InfoFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationBarView.OnItemSelectedListener, NavigationBarView.OnItemReselectedListener {

    private ActivityMainBinding binding;

    private CommonOperations common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        common = new CommonOperations(getBaseContext());

        setSupportActionBar(binding.toolbar);

        binding.navBottom.setOnItemSelectedListener(this);
        binding.navBottom.setOnItemReselectedListener(this);

        if (savedInstanceState == null) {
            replaceFragment(new InfoFragment());
        }

        binding.adView.loadAd(new AdRequest.Builder().build());

        binding.btnHotGetLocation.setOnClickListener(this::locationAction);
        binding.btnHotLock.setOnClickListener(view -> lockAction());
        binding.btnHotUnlock.setOnClickListener(view -> unlockAction());
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        selectItem(menuItem);
        return true;
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
        selectItem(menuItem);
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
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_frame, fragment).commit();
    }

}
