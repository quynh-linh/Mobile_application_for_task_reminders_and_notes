package com.example.doan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.Retrofit2.APIUtils;
import com.example.doan.Retrofit2.DataCilent;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class registerActivity extends AppCompatActivity {
    EditText editTextNameLogin , editTextPass , editTextEnterPass;
    Button btnRegister;
    TextView textviewNotify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        AnhXa();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameLogin = editTextNameLogin.getText().toString().trim();
                String pass = editTextPass.getText().toString().trim();
                String enterPass = editTextEnterPass.getText().toString().trim();
                if (nameLogin.isEmpty() || pass.isEmpty() || enterPass.isEmpty()){
                    textviewNotify.setTextColor(Color.RED);
                    textviewNotify.setText("Vui lòng nhập đầy đủ thông tin");
                } else {
                    if(pass.equals(enterPass) == true){
                        addUser(nameLogin,pass);
                    } else {
                        textviewNotify.setTextColor(Color.RED);
                        textviewNotify.setText("Mập khẩu không trùng khớp !");
                    }
                }
            }
        });
    }
    public void AnhXa(){
        editTextNameLogin = (EditText) findViewById(R.id.editTextNameLogin);
        editTextPass = (EditText) findViewById(R.id.editTextTextPassword);
        editTextEnterPass = (EditText) findViewById(R.id.editTextTextEnterPassword);
        textviewNotify = (TextView) findViewById(R.id.textviewNotify);
        btnRegister = (Button) findViewById(R.id.buttonRegister);
    }
    public  void addUser(String nameLogin , String password){
        DataCilent dataCilent = APIUtils.getData();
        Call<String> call = dataCilent.addUser(nameLogin,password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String result = response.body();
                if(result.equals("success")){
                    startActivity(new Intent(registerActivity.this,LoginActivity.class));
                }
                else if(result.equals("exists")){
                    textviewNotify.setTextColor(Color.RED);
                    textviewNotify.setText("Tên đăng nhập đã tồn tại");
                }
                else if(result.equals("error")){
                    textviewNotify.setTextColor(Color.RED);
                    textviewNotify.setText("Thêm không thành công");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error Add User",t.getMessage());
            }
        });
    }
}