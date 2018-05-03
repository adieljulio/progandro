package com.adiel.gogogicha;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Adiel on 4/27/2018.
 */

public class RideActivity extends AppCompatActivity {
    private TextView txvOrigin;
    private TextView txvDest;
    private Button btnScanDest;
    private Button btnDone;
    private TextView txvBoardingTime;
    private TextView txvArrivedTime;
    private DrawerLayout mDrawerLayout;
    private FirebaseDatabase db;
    private DatabaseReference dbUser;
    private DatabaseReference dbHistory;
    private DatabaseReference dbHistoryNow;
    private DatabaseReference dbSta;
    private String code;

    String gatein;
    String gateout;
    String timein ;
    String timeout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        /*if(!User.internetCheck()){
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);

        }*/

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

        txvOrigin = (TextView)findViewById(R.id.txvOrigin);
        txvDest = (TextView)findViewById(R.id.txvDest);
        btnScanDest = (Button)findViewById(R.id.btnScanDest);
        txvBoardingTime = (TextView)findViewById(R.id.txvBoardingTime);
        txvArrivedTime = (TextView)findViewById(R.id.txvArrivedTime);
        btnDone = (Button)findViewById(R.id.btnDone);

        btnScanDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadScanView();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPaymentView();
            }
        });

        db = FirebaseDatabase.getInstance();
        dbUser = db.getReference("user").child(User.user);
        dbHistory = dbUser.child("history");
        if(getIntent().getExtras().getString("code")!=null){
            code = getIntent().getExtras().getString("code");
            final String timestamp = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss").format(new Date());
            if(code.split("_")[1].equals("in")) {
                if(User.ongoing.equals("")) {
                    dbHistory.child(code + timestamp).child("gatein").setValue(code);
                    dbHistoryNow = dbHistory.child(code + timestamp);
                    dbHistoryNow.child("timein").setValue(timestamp);
                    dbHistoryNow.child("timeout").setValue("");
                    dbHistoryNow.child("gateout").setValue("");
                    dbHistoryNow.child("ongoing").setValue(true);
                    dbUser.child("ongoing").setValue(code+timestamp);

                    setView();

                }
            }
            if(code.split("_")[1].equals("out")){
                /*
                dbUser.child("ongoing").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue().equals("")){

                        }else{
                            Intent intent = new Intent(this,RideActivity.class);
                            intent.putExtra("key",dataSnapshot.getValue().toString());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                })*/
                Log.e("U",User.ongoing);
                dbHistoryNow = dbHistory.child(User.ongoing);
                dbHistoryNow.child("gateout").setValue(code);
                dbHistoryNow.child("timeout").setValue(timestamp);
                dbHistoryNow.child("ongoing").setValue(false);
                setView();
                dbUser.child("ongoing").setValue("");

            }

        }
        if(getIntent().getExtras().getString("key")!=null){
            dbHistoryNow = dbHistory.child(getIntent().getExtras().getString("key"));
            setView();
        }



    }


    private void setView(){
        dbHistoryNow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gatein = dataSnapshot.child("gatein").getValue().toString();
                gateout = dataSnapshot.child("gateout").getValue().toString();
                timein = dataSnapshot.child("timein").getValue().toString();
                timeout = dataSnapshot.child("timeout").getValue().toString();
                String gateinparse[] = gatein.split("_");
                String gateoutparse[] = gateout.split("_");
                String timeinparse[] = timein.split("_");
                String timeoutparse[] = timeout.split("_");


                Log.d("D"," "+gatein+" "+gateout+" "+timein+" "+timeout+" ");

                for (int i=0 ;i<gateinparse.length;i++){
                    Log.d("I",i+" "+gateinparse[i]);
                }
                for (int i=0 ;i<gateoutparse.length;i++){
                    Log.d("I",i+" "+gateoutparse[i]);
                }
                for (int i=0 ;i<timeinparse.length;i++){
                    Log.d("I",i+" "+timeinparse[i]);
                }
                for (int i=0 ;i<timeoutparse.length;i++){
                    Log.d("I",i+" "+timeoutparse[i]);
                }
                DatabaseReference dbStaIn = db.getReference("sta").child(gateinparse[0]);
                dbStaIn.child("nama").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String nama =  dataSnapshot.getValue().toString();
                        txvOrigin.setText("Sta. " + nama);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



                if(gateout.equals("")){
                    txvDest.setText("-");
                }else{
                    DatabaseReference dbStaOut = db.getReference("sta").child(gateoutparse[0]);
                    dbStaOut.child("nama").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String nama =  dataSnapshot.getValue().toString();
                            txvDest.setText("Sta. " + nama);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if(!timein.equals(""))
                    txvBoardingTime.setText(timeinparse[4]+":"+timeinparse[5]+":"+timeinparse[6]+" "+timeinparse[3]+"/"+timeinparse[2]+"/"+timeinparse[1]);
                if(!timeout.equals(""))
                    txvArrivedTime.setText(timeoutparse[4]+":"+timeoutparse[5]+":"+timeoutparse[6]+" "+timeoutparse[3]+"/"+timeoutparse[2]+"/"+timeoutparse[1]);

                Log.e("D",gatein+" "+gateout);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void loadScanView(){
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadPaymentView(){
        User.origin = "";
        User.dest = "";
        User.arrivedTime = "";
        User.boardingTime = "";
        txvOrigin.setText("");
        txvDest.setText("");
        txvBoardingTime.setText("");
        txvArrivedTime.setText("");
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra("triggerView", "rideActivity");
        startActivity(intent);
        finish();
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
