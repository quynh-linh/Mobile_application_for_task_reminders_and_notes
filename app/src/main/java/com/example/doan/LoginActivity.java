package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.Retrofit2.APIUtils;
import com.example.doan.Retrofit2.DataCilent;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    Button btnlogin , btnsignup;
    EditText editTextNameLogin , editTextPass;
    TextView textViewNotify;
    Session session;
    public void AnhXa(){
        btnlogin = (Button) findViewById(R.id.buttonLogin);
        btnsignup = (Button) findViewById(R.id.buttonSignup);
        editTextNameLogin = (EditText) findViewById(R.id.editTextNameLogin);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        textViewNotify = (TextView) findViewById(R.id.textViewNotify);
        session = new Session(LoginActivity.this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        AnhXa();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameLogin = editTextNameLogin.getText().toString();
                String pass = editTextPass.getText().toString();
                if (!(nameLogin.isEmpty() || pass.isEmpty())){
                    loginUser(nameLogin,pass);
                    session.createLoginSession(nameLogin);
                    session.checkLogin();
                } else {
                    textViewNotify.setText("Vui lòng nhập đầy đủ thông tin !");
                    textViewNotify.setTextColor(Color.RED);
                }
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
    public void loginUser(String nameLogin,String password){
        DataCilent dataCilent = APIUtils.getData();
        Call<String> call = dataCilent.loginUser(nameLogin,password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String result = response.body();
                if (result.trim().equals("success")){
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                } else {
                    textViewNotify.setText("Đăng nhập thất bại");
                    textViewNotify.setTextColor(Color.RED);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("AAA",t.getMessage());
            }
        });
    }
}
