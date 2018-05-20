package com.adiel.gogogicha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnLoginGoogle;
    private TextView txvSignUp;
    private DrawerLayout mDrawerLayout;
    private boolean check;
    protected static SharedPreferences sp;
    protected static SharedPreferences.Editor edit;
    protected static FirebaseDatabase db = FirebaseDatabase.getInstance();;
    private DatabaseReference dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance();
        sp = this.getSharedPreferences("gogitcha",this.MODE_PRIVATE);
        edit = sp.edit();
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        //btnLoginGoogle = (Button)findViewById(R.id.btnLoginGoogle);
        txvSignUp = (TextView)findViewById(R.id.txvSignUp);

        initialize();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(txtUsername.getText().toString())) {
                            Log.d("login","ada");
                            String id = txtUsername.getText().toString();
                            String password = dataSnapshot.child(txtUsername.getText().toString()).child("password").getValue().toString();
                            if (password.equalsIgnoreCase(txtPassword.getText().toString())) {
                                edit.putString("id",id);
                                edit.commit();
                                loadHomeView();
                            } else {
                                Toast.makeText(getApplicationContext(), "Username or password incorrect", Toast.LENGTH_LONG).show();
                            }
                        } else {

                            Log.d("login","ga");
                            Toast.makeText(getApplicationContext(), "Username or password incorrect", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        /*btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        txvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSignUpView();
            }
        });
    }

    private void initialize(){
        if(sp.getString("id",null)!=null){
            User.user = sp.getString("id",null);
            loadHomeView();
        }
    }

    private void loadHomeView(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadSignUpView(){
        Intent intent = new Intent(this, SignUpActivity.class);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
