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

import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hp on 27/04/2018.
 */

public class PaymentActivity extends AppCompatActivity {
    private TextView txvPayment;
    private TextView txvPaymentTitle;
    private TextView txvBalance;
    private Button btnFinish;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_payment);

        txvPayment = (TextView)findViewById(R.id.txvPayment);
        txvPaymentTitle = (TextView)findViewById(R.id.txvPaymentTitle);
        txvBalance = (TextView)findViewById(R.id.txvBalance);
        btnFinish = (Button)findViewById(R.id.btnFinish);



        Toolbar toolbar = findViewById(R.id.payment_toolbar);
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

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHomeView();
            }
        });
        if(getIntent().getExtras().getString("code")!=null) {
            String code = getIntent().getExtras().getString("code");
            int cost = Integer.parseInt(code.split("_")[5]);
            if(cost > MainActivity.sp.getInt("balance",0)){
                txvPaymentTitle.setText("Yout Balance is Not Enough");
                txvPayment.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(0));
                txvBalance.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(MainActivity.sp.getInt("balance",0)));
            }else {
                int balance = MainActivity.sp.getInt("balance", 0) - cost;
                DatabaseReference dbUser = MainActivity.db.getReference("user").child(MainActivity.sp.getString("id", ""));
                dbUser.child("balance").setValue(MainActivity.sp.getInt("balance", 0) - cost);
                DatabaseReference dbHistory = dbUser.child("history");
                final String timestamp = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss").format(new Date());
                dbHistory.child(code + timestamp).child("gatein").setValue(code);
                DatabaseReference dbHistoryNow = dbHistory.child(code + timestamp);
                dbHistoryNow.child("timein").setValue(timestamp);
                dbHistoryNow.child("timeout").setValue("");
                dbHistoryNow.child("gateout").setValue("");
                dbHistoryNow.child("ongoing").setValue(false);
                dbHistoryNow.child("cost").setValue(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(cost));
                /*
                String[] parse = code.split("_");
                String url ="http://adieljulio:gogogicha@broker.shiftr.io/"+parse[1]+"/"+parse[2];
                new Send().execute(url,parse[3]);
                */
                txvPayment.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(cost));
                txvBalance.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(balance));
            }
        }
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
