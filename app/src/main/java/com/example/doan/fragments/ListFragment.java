package com.example.doan.fragments;

import static android.graphics.Color.rgb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.IpAddressWifi;
import com.example.doan.R;
import com.example.doan.Session;
import com.example.doan.Task;
import com.example.doan.TaskApdater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class ListFragment extends Fragment {
    ListView listView;
    TaskApdater taskApdater;
    ArrayList<Task> arrayListTasks;
    ImageButton btn_create;
    Session session;
    IpAddressWifi ipAddressWifi ;
    String url;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view=  inflater.inflate(R.layout.fragment_list, container, false);
        AnhXa(view);
        //Task task = new Task(name,description,dateStart,dateEnd,timeStart,timeEnd);
        //arrayListTasks.add(task);
        taskApdater = new TaskApdater(getActivity(),R.layout.row_listview_task,arrayListTasks);
        listView.setAdapter(taskApdater);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment createTask = new CreateTaskFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                fm.replace(R.id.constraint, createTask, "fragment");
                fm.commit();
            }
        });
        ReadJsonTask(url);
        return view;
    }
    private void AnhXa(View view){
        btn_create = (ImageButton) view.findViewById(R.id.btn_create);
        listView = (ListView) view.findViewById(R.id.listViewTask);
        arrayListTasks = new ArrayList<>();
        session = new Session(getActivity());
        ipAddressWifi = new IpAddressWifi();
        url = "http://"+ ipAddressWifi.getIp()+ipAddressWifi.getPortLocalHost()+"/"+ipAddressWifi.getFileNameDB()+"/selectJsonTask.php";
        Log.d("url",url);
    }
    private void ReadJsonTask(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0 ; i<response.length() ; i++){
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        String name = jsonObject.getString("name");
                        String des = jsonObject.getString("content");
                        String date = jsonObject.getString("date");
                        String time = jsonObject.getString("time");
                        String status = jsonObject.getString("check_status");
                        arrayListTasks.add(new Task(name,des,date,status,time));
                        taskApdater = new TaskApdater(getActivity(),R.layout.row_listview_task,arrayListTasks);
                        listView.setAdapter(taskApdater);
                        Log.d("Success list",response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Error list",e.toString());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error get json list",error.toString());
            }
        });
        requestQueue.add(arrayRequest);
    }
}