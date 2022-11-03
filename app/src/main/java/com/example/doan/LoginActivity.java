package com.example.doan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button btnlogin , btnsignup;
    EditText editTextNameLogin , editTextPass;
    TextView textViewNotify;
    Session session;
    IpAddressWifi ipAddressWifi ;
    String url;
    public void AnhXa(){
        btnlogin = (Button) findViewById(R.id.buttonLogin);
        btnsignup = (Button) findViewById(R.id.buttonSignup);
        editTextNameLogin = (EditText) findViewById(R.id.editTextNameLogin);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        textViewNotify = (TextView) findViewById(R.id.textViewNotify);
        session = new Session(LoginActivity.this);
        ipAddressWifi = new IpAddressWifi();
        url = "http://"+ ipAddressWifi.getIp()+ipAddressWifi.getPortLocalHost()+"/"+ipAddressWifi.getFileNameDB()+"/loginUser.php";
        Log.d("url",url);
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
                    loginUser(url);
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

    public void loginUser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("Login success")){
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    session.createLoginSession(editTextNameLogin.getText().toString());
                    session.checkLogin();
                } else {
                    textViewNotify.setText("Đăng nhập thất bại");
                    textViewNotify.setTextColor(Color.RED);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d("Error Login User",error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("nameLogin",editTextNameLogin.getText().toString());
                map.put("password",editTextPass.getText().toString());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
