package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    Button btnlogin , btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        AnhXa();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                startActivity(new Intent(LoginActivity.this,AboutActivity.class),options.toBundle());
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,registerActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                startActivity(intent,options.toBundle());
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });
    }
    public void AnhXa(){
        btnlogin = (Button) findViewById(R.id.buttonLogin);
        btnsignup = (Button) findViewById(R.id.buttonSignup);
    }
}