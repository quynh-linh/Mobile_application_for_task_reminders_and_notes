package com.example.doan.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.Retrofit2.APIUtils;
import com.example.doan.Retrofit2.DataCilent;
import com.example.doan.Session;

import java.util.Calendar;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class UpdateUserFragment extends Fragment {
    EditText editTextName,editTextPhone,editTextBirthDay;
    Button buttonUpdate;
    ImageButton imageButtonBack;
    TextView textViewNotify;
    Session session;
    String nameLogin ;
    public  void AnhXa(View view){
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextPhone = (EditText) view.findViewById(R.id.editTextPhone);
        editTextBirthDay = (EditText) view.findViewById(R.id.editTextBirthDay);
        buttonUpdate = (Button) view.findViewById(R.id.buttonUpdate);
        textViewNotify = (TextView) view.findViewById(R.id.textViewNotify);
        imageButtonBack = (ImageButton) view.findViewById(R.id.imageButtonBack);
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
        editTextBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String brithday = editTextBirthDay.getText().toString();
                if (name.isEmpty() || phone.isEmpty() || brithday.isEmpty()){
                    textViewNotify.setText("Vui lòng nhập đầy đủ thông tin");
                    textViewNotify.setTextColor(Color.RED);
                } else {
                    updateUser(name,Integer.valueOf(phone),brithday,nameLogin);
                }
            }
        });
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFragment userFragment = new UserFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                fm.replace(R.id.constraint,userFragment,"fragment");
                fm.commit();
            }
        });
        return view ;
    }
    public void selectDate() {
        //this method performs the date picker task
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                editTextBirthDay.setText(day + "-" + (month + 1) + "-" + year);
                //sets the selected date as test for button
            }
        }, year, month, day);
        datePickerDialog.show();
    }
    public  void updateUser(String fullName , int phone , String date , String nameLogin){
        DataCilent dataCilent = APIUtils.getData();
        Call<String> call = dataCilent.updateUser(fullName,phone,date,nameLogin);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String result = response.body();
                if (result.equals("success")){
                    UserFragment userFragment = new UserFragment();
                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                    fm.replace(R.id.constraint,userFragment,"fragment");
                    fm.commit();
                } else {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                    textViewNotify.setText("Cập nhập không thành công");
                    textViewNotify.setTextColor(Color.RED);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("AAA",t.getMessage());
            }
        });
    }
}