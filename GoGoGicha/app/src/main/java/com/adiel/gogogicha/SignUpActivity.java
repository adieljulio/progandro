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
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by hp on 30/04/2018.
 */

public class SignUpActivity extends AppCompatActivity {
    private EditText txtUsernameSignup;
    private EditText txtEmailSignup;
    private EditText txtPasswordSignup;
    private Button btnSignUp;
    private DrawerLayout mDrawerLayout;
    private FirebaseDatabase db;
    private DatabaseReference dbUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_signup);

        txtUsernameSignup = (EditText)findViewById(R.id.txtUsernameSignup);
        txtEmailSignup = (EditText)findViewById(R.id.txtEmailSignup);
        txtPasswordSignup = (EditText)findViewById(R.id.txtPasswordSignup);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        final DatabaseReference dbUser = MainActivity.db.getReference("user");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtUsernameSignup.getText().toString().equalsIgnoreCase("")){
                    if(!txtEmailSignup.getText().toString().equalsIgnoreCase("")&&isValidEmail(txtEmailSignup.getText().toString())){
                        if(!txtPasswordSignup.getText().toString().equalsIgnoreCase("")){
                            dbUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(!dataSnapshot.hasChild(txtUsernameSignup.getText().toString())){
                                        dbUser.child(txtUsernameSignup.getText().toString()).child("password").setValue(txtPasswordSignup.getText().toString());
                                        dbUser.child(txtUsernameSignup.getText().toString()).child("email").setValue(txtEmailSignup.getText().toString());
                                        dbUser.child(txtUsernameSignup.getText().toString()).child("ongoing").setValue("");
                                        dbUser.child(txtUsernameSignup.getText().toString()).child("balance").setValue(0);

                                        Toast.makeText(getApplicationContext(), "Signup Success", Toast.LENGTH_LONG).show();
                                        loadMain();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Username already exist", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(), "Field cannot Empty", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Field cannot empty or Invalid format", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Field cannot Empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
