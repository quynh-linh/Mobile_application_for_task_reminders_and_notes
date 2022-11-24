package com.example.doan.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.doan.IpAddressWifi;
import com.example.doan.R;
import com.example.doan.Retrofit2.APIUtils;
import com.example.doan.Retrofit2.DataCilent;
import com.example.doan.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class UserFragment extends Fragment {
    public TextView textViewName , textViewBirthDay , textViewPhone;
    Button btnLogout ,btnUpdate;
    Session session;
    String nameLogin;
    IpAddressWifi ipAddressWifi;
    ImageView imageViewUser;
    String url;
    String real_Path = "";
    Bitmap bitmap;
    int REQUEST_CODE_IMAGE = 123;
    public void AnhXa (View view){
        textViewName =(TextView) view.findViewById(R.id.textViewName);
        textViewBirthDay =(TextView) view.findViewById(R.id.textViewNgaySinh);
        textViewPhone =(TextView) view.findViewById(R.id.textViewPhone);
        btnLogout = (Button) view.findViewById(R.id.button_logout);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        imageViewUser = (ImageView) view.findViewById(R.id.imageViewUser);
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
        imageViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
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
                    Glide.with(imageViewUser.getContext()).load(APIUtils.baseUrl+"image/"+jsonObject.getString("image")).into(imageViewUser);
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
    public void ChooseImage(){
        PopupMenu popupMenu = new PopupMenu(getActivity(),imageViewUser);
        popupMenu.getMenuInflater().inflate(R.menu.menu_choose_image_user,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.chooseImage:
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,REQUEST_CODE_IMAGE);
                        break;
                    case R.id.takeImage:

                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode== REQUEST_CODE_IMAGE && resultCode==RESULT_OK && data != null)
        {
            Uri uri =data.getData();
            real_Path = getRealPathFromURI(uri);
            try
            {
                InputStream inputStream= getActivity().getContentResolver().openInputStream(uri);
                bitmap= BitmapFactory.decodeStream(inputStream);
                imageViewUser.setImageBitmap(bitmap);
                updatImageUser();
            }catch (Exception ex)
            {

            }
        }

        super .onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
    public void updatImageUser(){
        Map<String,String> mapSession = session.getUserDetails();
        nameLogin = mapSession.get("user_name").toString().trim();
            File file = new File(real_Path);
            String file_path = file.getAbsolutePath();
            String [] mang_ten_file = file_path.split("\\.");
            file_path = mang_ten_file[0] + System.currentTimeMillis() + "." + mang_ten_file[1];
            Log.d("BBB",file_path);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipath/from-data"),file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload_file",file_path,requestBody);
            DataCilent dataCilent = APIUtils.getData();
            // up load file ảnh lên folder trong PHP
            Call<String> call = dataCilent.upLoadPhoto(body);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    if (response != null){
                        // message trả về cái tên của file ảnh
                        String message = response.body();
                        if (message.length()>0){
                            Call<String> call1 = dataCilent.updateImageUser(message,nameLogin);
                            call1.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                    String result = response.body();
                                    if (result.trim().equals("success")){
                                        Toast.makeText(getActivity(), "Cập nhập ảnh thành công", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(getActivity(), "Cập nhập ảnh không thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.d("Error add", t.getMessage().toString());
                                }
                            });
                        }
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("Error", t.getMessage().toString());
                }
            });
    }
}