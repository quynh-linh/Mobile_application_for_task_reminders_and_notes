package com.example.doan.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.doan.R;
import com.example.doan.Session;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserFragment extends Fragment {
    EditText editTextName,editTextPhone,editTextBirthDay;
    Button buttonUpdate;
    TextView textViewNotify;
    String url = "http://192.168.1.39:8080/Mobile_App/updateUser.php";
    Session session;
    String nameLogin ;
    public  void AnhXa(View view){
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextPhone = (EditText) view.findViewById(R.id.editTextPhone);
        editTextBirthDay = (EditText) view.findViewById(R.id.editTextBirthDay);
        buttonUpdate = (Button) view.findViewById(R.id.buttonUpdate);
        textViewNotify = (TextView) view.findViewById(R.id.textViewNotify);
        session = new Session(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_user, container, false);
        AnhXa(view);
        Bundle bundle = getArguments();
        editTextName.setText(bundle.getString("fullname"));
        editTextBirthDay.setText(bundle.getString("ngaySinh"));
        editTextPhone.setText(bundle.getString("phone"));
        Map<String,String > mapSession = session.getUserDetails();
        nameLogin = mapSession.get("user_name").toString().trim();
        Toast.makeText(getActivity(), nameLogin.toString(), Toast.LENGTH_SHORT).show();
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String brithday = editTextBirthDay.getText().toString();
                if ( name.isEmpty() || phone.isEmpty() || brithday.isEmpty()){
                    textViewNotify.setText("Vui lòng nhập đầy đủ thông tin");
                    textViewNotify.setTextColor(Color.RED);
                } else {
                    updateUser(url);
                }
            }
        });
        return view ;
    }
    public  void updateUser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("Update success")){
                    textViewNotify.setText("Cập nhập thành công");
                    textViewNotify.setTextColor(Color.GREEN);
                } else {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                    textViewNotify.setText("Cập nhập không thành công");
                    textViewNotify.setTextColor(Color.RED);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error update",error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("fullname",editTextName.getText().toString());
                map.put("phone",editTextPhone.getText().toString());
                map.put("birthday",editTextBirthDay.getText().toString());
                map.put("nameLogin",nameLogin);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}