package com.adiel.gogogicha;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Adiel on 4/27/2018.
 */

public class RideActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference dbUser;
    private DatabaseReference dbHistory;
    private DatabaseReference dbHistoryNow;
    private String ongoing;
    private String code;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
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
                    dbHistoryNow.child("gateout").setValue("null");
                    dbHistoryNow.child("ongoing").setValue(true);
                    dbUser.child("ongoing").setValue(code+timestamp);
                }
            }
            if(code.split("_")[1].equals("out")){
                dbUser.child("ongoing").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User.ongoing=dataSnapshot.getValue().toString();
                        ongoing = dataSnapshot.getValue().toString();

                        Log.e("I",""+ongoing);
                        if(!dataSnapshot.getValue().equals("")) {
                            dbHistoryNow = dbHistory.child(ongoing);
                            dbHistoryNow.child("gateout").setValue(code);
                            dbHistoryNow.child("timeout").setValue(timestamp);
                            dbHistoryNow.child("ongoing").setValue(false);
                            dbUser.child("ongoing").setValue("");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        }
        if(getIntent().getExtras().getString("key")!=null){
            dbHistoryNow = dbHistory.child(getIntent().getExtras().getString("key"));

        }
    }
}
