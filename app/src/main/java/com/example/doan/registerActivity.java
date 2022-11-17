package com.example.doan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class registerActivity extends AppCompatActivity {
    EditText editTextNameLogin , editTextPass , editTextEnterPass;
    Button btnRegister;
    TextView textviewNotify;
    IpAddressWifi ipAddressWifi ;
    String url;
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
                String enterpass = editTextEnterPass.getText().toString().trim();
                if (nameLogin.isEmpty() || pass.isEmpty() || enterpass.isEmpty()){
                    textviewNotify.setTextColor(Color.RED);
                    textviewNotify.setText("Vui lòng nhập đầy đủ thông tin");
                } else {
                    int checkPass = Integer.valueOf(pass.length());
                    Toast.makeText(registerActivity.this, ""+checkPass, Toast.LENGTH_SHORT).show();
                    if(pass.equals(enterpass) == true){
                        addUser(url);
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
        ipAddressWifi = new IpAddressWifi();
        url = "http://"+ ipAddressWifi.getIp()+ipAddressWifi.getPortLocalHost()+"/"+ipAddressWifi.getFileNameDB()+"/insertUser.php";
        Log.d("url",url);
    }
    public  void addUser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            textviewNotify.setTextColor(Color.GREEN);
                            textviewNotify.setText("Thêm thành công");
                        }
                        else if(response.trim().equals("Id already exists")){
                            textviewNotify.setTextColor(Color.RED);
                            textviewNotify.setText("Tên đăng nhập đã tồn tại");
                        }
                        else {
                            textviewNotify.setTextColor(Color.RED);
                            textviewNotify.setText("Thêm không thành công");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(registerActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d("Error Add User",error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                String nameLogin = editTextNameLogin.getText().toString().trim();
                String pass = editTextPass.getText().toString().trim();
                map.put("nameLogin",nameLogin);
                map.put("password",pass);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}