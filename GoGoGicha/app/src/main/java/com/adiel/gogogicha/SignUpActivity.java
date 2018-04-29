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
import android.widget.EditText;

/**
 * Created by hp on 30/04/2018.
 */

public class SignUpActivity extends AppCompatActivity {
    private EditText txtUsernameSignup;
    private EditText txtEmailSignup;
    private EditText txtPasswordSignup;
    private Button btnSignUp;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_signup);

        txtUsernameSignup = (EditText)findViewById(R.id.txtUsernameSignup);
        txtEmailSignup = (EditText)findViewById(R.id.txtEmailSignup);
        txtPasswordSignup = (EditText)findViewById(R.id.txtPasswordSignup);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHomeView();
            }
        });
    }

    private void loadHomeView(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


}
