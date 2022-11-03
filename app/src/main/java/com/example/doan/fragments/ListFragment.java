package com.example.doan.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.IpAddressWifi;
import com.example.doan.R;
import com.example.doan.Session;
import com.example.doan.Task;
import com.example.doan.TaskApdater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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

        Map<String,String> mapSes = session.getUserDetails();
        String nameLogin = mapSes.get("user_name").trim();
        ReadJsonTask(url,nameLogin);
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
        if (listView != null){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",arrayListTasks.get(i).getName().toString().trim());
                    bundle.putString("time",arrayListTasks.get(i).getTime().toString().trim());
                    bundle.putString("date",arrayListTasks.get(i).getDate().toString().trim());
                    bundle.putString("content",arrayListTasks.get(i).getDescription().toString().trim());
                    bundle.putString("nameLogin",nameLogin);
                    bundle.putString("id",arrayListTasks.get(i).getId().toString().trim());
                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    UpdateAndRemoveTaskFragment updateAndRemoveTaskFragment = new UpdateAndRemoveTaskFragment();
                    updateAndRemoveTaskFragment.setArguments(bundle);
                    fm.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                    fm.replace(R.id.constraint,updateAndRemoveTaskFragment,"fragment");
                    fm.commit();
                }
            });
        }
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
    private void ReadJsonTask(String url, String nameLogin){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest arrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0 ; i< jsonArray.length() ; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String des = jsonObject.getString("content");
                        String date = jsonObject.getString("date");
                        String time = jsonObject.getString("time");
                        String user_id = jsonObject.getString("user_id");
                        String id = jsonObject.getString("id");
                        arrayListTasks.add(new Task(id,name,des,date,time,user_id));
                        taskApdater = new TaskApdater(getActivity(),R.layout.row_listview_task,arrayListTasks);
                        listView.setAdapter(taskApdater);
                    }
                    Log.d("Success list",response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Success list",error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("nameLogin",nameLogin);
                return map;
            }
        };
        requestQueue.add(arrayRequest);
    }
}