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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by hp on 27/04/2018.
 */

public class HomeActivity extends AppCompatActivity {
    private ImageView imvOnRide;
    private ImageView imvBuying;
    private ImageView imvHistory;
    private ImageView imvAccount;
    private TextView txvOnRide;
    private TextView txvBuying;
    private TextView txvHistory;
    private TextView txvAccount;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home);
        FirebaseDatabase.getInstance().getReference("user").child(User.user).child("ongoing").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User.ongoing = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
                        //menuItem.setChecked(true);
                        // close drawer when item is tapped
                        //mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        int id = menuItem.getItemId();
                        if (id == R.id.nav_home){
                            loadHomeView();
                        } else if (id == R.id.nav_ride){
                            loadOnRideView();
                        } else if (id == R.id.nav_history) {
                            loadHistoryView();
                        } else if (id == R.id.nav_account){
                            loadAccountView();
                        } else if (id == R.id.nav_logout){
                            loadLoginView();
                        }

                        return true;
                    }
                });

        imvOnRide = (ImageView)findViewById(R.id.imvOnRide);
        imvBuying = (ImageView)findViewById(R.id.imvBuying);
        imvHistory = (ImageView)findViewById(R.id.imvHistory);
        imvAccount = (ImageView)findViewById(R.id.imvAccount);
        txvOnRide = (TextView)findViewById(R.id.txvOnRide);
        txvBuying = (TextView)findViewById(R.id.txvBuying);
        txvHistory = (TextView)findViewById(R.id.txvHistory);
        txvAccount = (TextView)findViewById(R.id.txvAccount);

        imvOnRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOnRideView();
            }
        });

        imvBuying.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loadBuyingActivity();
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

        txvBuying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadBuyingActivity();
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
        Log.d("D","/"+User.ongoing+"/");
        if (User.ongoing.equals("")) {
            Intent intent = new Intent(this, ScanActivity.class);
            startActivity(intent);
        } else{
            Intent intent = new Intent(this, RideActivity.class);
            intent.putExtra("key", User.ongoing);
            startActivity(intent);
        }


    }

    private void loadHistoryView(){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    private void loadAccountView(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    private void loadHomeView(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadLoginView(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadBuyingActivity(){
        Intent intent = new Intent(this, BuyingActivity.class);
        startActivity(intent);
        finish();
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

