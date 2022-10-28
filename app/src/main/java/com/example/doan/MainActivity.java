package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.example.doan.fragments.HomeFragment;
import com.example.doan.fragments.LibFragment;
import com.example.doan.fragments.ListFragment;
import com.example.doan.fragments.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.ic_home);
    }
    HomeFragment homeFragment = new HomeFragment();
    ListFragment listFragment = new ListFragment();
    LibFragment libFragment = new LibFragment();
    UserFragment userFragment = new UserFragment();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ic_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.constraint, homeFragment).commit();
                return true;

            case R.id.ic_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.constraint, listFragment).commit();
                return true;

            case R.id.ic_lib:
                getSupportFragmentManager().beginTransaction().replace(R.id.constraint, libFragment).commit();
                return true;
            case R.id.ic_user:
                getSupportFragmentManager().beginTransaction().replace(R.id.constraint, userFragment).commit();
                return true;
        }
        return false;
    }
}