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
        List<History> listFilm = new ArrayList<History>();
        historyAdapter = new HistoryAdapter(listFilm, this);

        //menggabungkan RecyclerView dengan FilmAdapter
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rcyHistory.setLayoutManager(lm);
        rcyHistory.setItemAnimator(new DefaultItemAnimator());
        rcyHistory.setAdapter(historyAdapter);

        dbUser = db.getReference("user").child(User.user);
        dbHistory = dbUser.child("history");

        final List<History> historyList = new ArrayList<History>();

        dbHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                History history = dataSnapshot.getValue(History.class);
                historyList.add(history);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        historyAdapter.notifyDataSetChanged();
    }

    private void loadOnRideView(){
        if (User.ongoing.equalsIgnoreCase("")) {
            Intent intent = new Intent(this, GetRideActivity.class);
            startActivity(intent);
        } else{
            loadHistoryView();
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
