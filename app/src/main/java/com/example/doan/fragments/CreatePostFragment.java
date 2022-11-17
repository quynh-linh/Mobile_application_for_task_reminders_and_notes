package com.example.doan.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.doan.Adapter.DinaryAdapter;
import com.example.doan.Adapter.TaskApdater;
import com.example.doan.R;
import com.example.doan.Retrofit2.APIUtils;
import com.example.doan.Retrofit2.DataCilent;
import com.example.doan.Session;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostFragment extends Fragment {
    EditText editTextTitlePost,editTextDesPost,editTextChooseImagePost,editTextFellStatusPost;
    ImageView imageViewPost;
    int REQUEST_CODE_IMAGE = 123;
    Button buttonPost;
    String real_Path = "";
    Bitmap bitmap;
    Session session;
    String nameLogin;
    public void AnhXa(View view){
        editTextChooseImagePost = (EditText) view.findViewById(R.id.editTextChooseImagePost);
        editTextTitlePost = (EditText) view.findViewById(R.id.editTextTitlePost);
        editTextDesPost = (EditText) view.findViewById(R.id.editTextDesPost);
        editTextFellStatusPost = (EditText) view.findViewById(R.id.editTextFellStatusPost);
        imageViewPost = (ImageView) view.findViewById(R.id.imageViewPost);
        buttonPost = (Button) view.findViewById(R.id.buttonPost);
        session = new Session(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);
        AnhXa(view);
        editTextChooseImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });
        editTextFellStatusPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseStatus();
            }
        });
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost();
            }
        });
        return view;
    }
    public void addPost(){
        // tạo ngày và giờ
        Calendar calendar = Calendar.getInstance();
        //
        String title = editTextTitlePost.getText().toString().trim();
        String date = calendar.getTime().toString();
        String content = editTextDesPost.getText().toString().trim();
        String status = editTextFellStatusPost.getText().toString().trim();
        int find_id =0;
        if (status.equals("Vui")){
            find_id =1;
        } else if (status.equals("Buồn")){
            find_id =2;
        }else if (status.equals("Tức giận")){
            find_id =3;
        }else if (status.equals("Hạnh phúc")){
            find_id =4;
        }
        Map<String,String> mapSession = session.getUserDetails();
        nameLogin = mapSession.get("user_name").toString().trim();
        if (title != "" || date != "" || date != "" || content != "" ){
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
            int finalFind_id = find_id;
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    if (response != null){
                        // message trả về cái tên của file ảnh
                        String message = response.body();
                        if (message.length()>0){
                            Call<String> call1 = dataCilent.insertDataNote(title,content,date,message,nameLogin,String.valueOf(finalFind_id));
                            call1.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String result = response.body();
                                    if (result.trim().equals("success")){
                                        Toast.makeText(getActivity(), "Đăng thành công", Toast.LENGTH_SHORT).show();
                                        LibFragment libFragment = new LibFragment();
                                        DinaryAdapter dinaryAdapter = libFragment.dinaryAdapter;
                                        dinaryAdapter.notifyDataSetChanged();
                                        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                                        fm.replace(R.id.constraint,libFragment,"fragment");
                                        fm.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                                        fm.commit();
                                    } else {
                                        Toast.makeText(getActivity(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
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
        } else {
            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if(requestCode== REQUEST_CODE_IMAGE && resultCode==RESULT_OK && data != null)
            {
                Uri uri =data.getData();
                real_Path = getRealPathFromURI(uri);
                try
                {
                    InputStream inputStream= getActivity().getContentResolver().openInputStream(uri);
                    bitmap= BitmapFactory.decodeStream(inputStream);
                    imageViewPost.setImageBitmap(bitmap);
                }catch (Exception ex)
                {

                }
            }

        super .onActivityResult(requestCode, resultCode, data);
    }
    // lấy đường dẫn thực của ảnh
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
    public void ChooseStatus(){
        PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(),editTextFellStatusPost);
        popupMenu.getMenuInflater().inflate(R.menu.menu_choose_status_post,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.fun:
                        editTextFellStatusPost.setText(menuItem.getTitle());
                        break;
                    case R.id.sad:
                        editTextFellStatusPost.setText(menuItem.getTitle());
                        break;
                    case R.id.angry:
                        editTextFellStatusPost.setText(menuItem.getTitle());
                        break;
                    case R.id.happy:
                        editTextFellStatusPost.setText(menuItem.getTitle());
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}