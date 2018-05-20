package com.adiel.gogogicha;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 27/04/2018.
 */

public class HistoryActivity extends AppCompatActivity {
    List<History> historyList;
    private HistoryAdapter historyAdapter;
    private RecyclerView rcyHistory;
    private DrawerLayout mDrawerLayout;
    private FirebaseDatabase db;
    private DatabaseReference dbUser;
    private DatabaseReference dbHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = findViewById(R.id.history_toolbar);
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

        //instansiasi atribut
        rcyHistory= (RecyclerView)findViewById(R.id.rcyHistory);

        historyList = new ArrayList<History>();
        historyAdapter = new HistoryAdapter(historyList, this);

        //menggabungkan RecyclerView dengan FilmAdapter
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rcyHistory.setLayoutManager(lm);
        rcyHistory.setItemAnimator(new DefaultItemAnimator());
        rcyHistory.setAdapter(historyAdapter);
        db = FirebaseDatabase.getInstance();
        dbUser = db.getReference("user").child(MainActivity.sp.getString("id",""));
        dbHistory = dbUser.child("history");


        dbHistory.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                historyList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    if(data.child("ongoing").getValue()!=null){
                        History history = new History(data.getKey(), data.child("timein").getValue().toString(), data.child("timeout").getValue().toString(), data.child("gatein").getValue().toString(), data.child("gateout").getValue().toString(), data.child("cost").getValue().toString());
                        historyList.add(history);
                    }
                }
                historyAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadOnRideView(){
        if (MainActivity.sp.getString("ongoing","").equals("")) {
            Intent intent = new Intent(this, ScanActivity.class);
            startActivity(intent);
        } else{
            Intent intent = new Intent(this, RideActivity.class);
            intent.putExtra("key", MainActivity.sp.getString("ongoing",""));
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
