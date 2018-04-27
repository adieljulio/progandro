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
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by hp on 27/04/2018.
 */

public class RideActivity2 extends AppCompatActivity {
    private TextView txvOrigin;
    private TextView txvDest;
    private Button btnScanOri;
    private Button btnScanDest;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_ride);

        Toolbar toolbar = findViewById(R.id.ride_toolbar);
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

        txvOrigin = (TextView)findViewById(R.id.txvOrigin);
        txvDest = (TextView)findViewById(R.id.txvDest);
        btnScanOri = (Button)findViewById(R.id.btnScanOri);
        btnScanDest = (Button)findViewById(R.id.btnScanDest);

        btnScanOri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadScanView();
            }
        });

        btnScanDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadScanView();
            }
        });
    }

    private void loadScanView(){
        Intent intent = new Intent(this, ScanActivity.class);
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
