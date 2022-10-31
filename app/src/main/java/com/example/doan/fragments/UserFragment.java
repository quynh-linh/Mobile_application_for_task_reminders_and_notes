package com.example.doan.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.IpAddressWifi;
import com.example.doan.LoginActivity;
import com.example.doan.R;
import com.example.doan.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserFragment extends Fragment {
    public TextView textViewName , textViewBirthDay , textViewPhone;
    Button btnLogout ,btnUpdate;
    Session session;
    String nameLogin;
    IpAddressWifi ipAddressWifi ;
    String url;
    public void AnhXa (View view){
        textViewName =(TextView) view.findViewById(R.id.textViewName);
        textViewBirthDay =(TextView) view.findViewById(R.id.textViewNgaySinh);
        textViewPhone =(TextView) view.findViewById(R.id.textViewPhone);
        btnLogout = (Button) view.findViewById(R.id.button_logout);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        session = new Session(getActivity());
        ipAddressWifi = new IpAddressWifi();
        url = "http://"+ ipAddressWifi.getIp()+ipAddressWifi.getPortLocalHost()+"/"+ipAddressWifi.getFileNameDB()+"/selectUser.php";
        Log.d("url",url);
    }
    @Override
    @SuppressLint("ResourceType")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fView = inflater.inflate(R.layout.fragment_user, container, false);
        AnhXa(fView);
        Map<String,String> mapSession = session.getUserDetails();
        nameLogin = mapSession.get("user_name").toString().trim();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               session.logoutUser();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUserFragment fragment = new UpdateUserFragment();
                Bundle bundle = new Bundle();
                bundle.putString("fullname",textViewName.getText().toString());
                bundle.putString("ngaySinh",textViewBirthDay.getText().toString());
                bundle.putString("phone",textViewPhone.getText().toString());
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                transaction.replace(R.id.constraint,fragment);
                transaction.commit();
            }
        });
        ReadJsonUser(url);
        return fView;
    }
    public void ReadJsonUser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    textViewName.setText(jsonObject.getString("name"));
                    textViewBirthDay.setText(jsonObject.getString("ngaySinh"));
                    textViewPhone.setText(jsonObject.getString("phone"));
                } catch (JSONException e) {
                    Log.v("JSonErr", e.getMessage());
            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("user_name",nameLogin);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}