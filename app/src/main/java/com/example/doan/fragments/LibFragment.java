package com.example.doan.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.Adapter.DinaryAdapter;
import com.example.doan.IpAddressWifi;
import com.example.doan.Model.DinaryModel;
import com.example.doan.R;
import com.example.doan.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LibFragment extends Fragment {
    public static ListView listViewPost;
    public static DinaryAdapter dinaryAdapter;
    ArrayList<DinaryModel> arrayListDinary;
    EditText editTextPost;
    Session session;
    IpAddressWifi ipAddressWifi ;
    String url ,nameLogin ;
    public void AnhXa(View view){
        editTextPost = (EditText) view.findViewById(R.id.editTextPost);
        listViewPost = (ListView) view.findViewById(R.id.listViewPost);
        arrayListDinary = new ArrayList<>();
        session = new Session(getActivity());
        ipAddressWifi= new IpAddressWifi();
        url = "http://"+ ipAddressWifi.getIp()+ipAddressWifi.getPortLocalHost()+"/"+ipAddressWifi.getFileNameDB()+"/selectJsonPost.php";
        Log.d("url",url);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lib, container, false);
        AnhXa(view);
        editTextPost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    Fragment fragment = new CreatePostFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.constraint,fragment).commit();
                }
            }
        });
        HashMap<String,String> map = session.getUserDetails();
        nameLogin = map.get("user_name").trim();
        ReadJsonPost(url,nameLogin);
        listViewPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), arrayListDinary.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        return view ;
    }
    private void ReadJsonPost(String url, String nameLogin){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest arrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0 ; i < jsonArray.length() ; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String content = jsonObject.getString("content");
                        String title = jsonObject.getString("title");
                        String date = jsonObject.getString("date");
                        String image = jsonObject.getString("image");
                        String user_id = jsonObject.getString("user_id");
                        String find_id = jsonObject.getString("find_id");
                        String id = jsonObject.getString("id");
                        arrayListDinary.add(new DinaryModel(id,content,image,find_id,title,date,user_id));
                        dinaryAdapter = new DinaryAdapter(getActivity(),arrayListDinary,R.layout.activity_row_listview_lib);
                        listViewPost.setAdapter(dinaryAdapter);
                    }
                    dinaryAdapter.notifyDataSetChanged();
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