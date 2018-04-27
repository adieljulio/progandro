package com.adiel.gogogicha;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hp on 27/04/2018.
 */

public class HomeActivity extends AppCompatActivity {
    private ImageView imvOnRide;
    private ImageView imvHistory;
    private ImageView imvAccount;
    private TextView txvOnRide;
    private TextView txvHistory;
    private TextView txvAccount;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        imvOnRide = (ImageView)findViewById(R.id.imvOnRide);
        imvHistory = (ImageView)findViewById(R.id.imvHistory);
        imvAccount = (ImageView)findViewById(R.id.imvAccount);
        txvOnRide = (TextView)findViewById(R.id.txvOnRide);
        txvHistory = (TextView)findViewById(R.id.txvHistory);
        txvAccount = (TextView)findViewById(R.id.txvAccount);

        imvOnRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOnRideView();
            }
        });

        imvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHistoryView();
            }
        });

        imvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAccountView();
            }
        });

        txvOnRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOnRideView();
            }
        });

        txvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHistoryView();
            }
        });

        txvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAccountView();
            }
        });
    }

    private void loadOnRideView(){
        Intent intent = new Intent(this, RideActivity.class);
        startActivity(intent);
    }

    private void loadHistoryView(){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    private void loadAccountView(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

