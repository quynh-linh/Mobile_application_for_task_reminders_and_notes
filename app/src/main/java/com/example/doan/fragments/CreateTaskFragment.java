package com.example.doan.fragments;

import static java.lang.String.valueOf;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.doan.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.SimpleFormatter;

public class CreateTaskFragment extends Fragment {
    public ImageButton btnDateStart , btnTaskStart , btnTaskEnd , btnDateEnd ;
    public Button btnCreateTask;
    public TextView textViewDate , textViewTaskStart , textViewTaskEnd , textViewDateEnd;
    public EditText editTextName ,editTextDes;
    public CreateTaskFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view=  inflater.inflate(R.layout.fragment_create_task, container, false);
        AnhXa(view);
        btnDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDateStart();
            }
        });
        btnTaskStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChonGioTaskStart();
            }
        });
        btnTaskEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonGioTaskEnd();
            }
        });
        btnDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDateEnd();
            }
        });
        btnCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListFragment fragment = new ListFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("name",editTextName.getText().toString());
                bundle.putString("description",editTextDes.getText().toString());
                bundle.putString("dateStart",textViewDate.getText().toString());
                bundle.putString("dateEnd",textViewDateEnd.getText().toString());
                bundle.putString("timeStart",textViewTaskStart.getText().toString());
                bundle.putString("timeEnd",textViewTaskEnd.getText().toString());
                fragment.setArguments(bundle);
                fm.replace(R.id.constraint,fragment,"fragment");
                fm.commit();
            }
        });
        return view;
    }
    private  void AnhXa(View view){
        // Image Button
        btnDateStart = (ImageButton) view.findViewById(R.id.imageButtonDate);
        btnTaskStart = (ImageButton) view.findViewById(R.id.imageBtnTasStart);
        btnTaskEnd = (ImageButton) view.findViewById(R.id.imageBtnTaskEnd);
        btnDateEnd = (ImageButton) view.findViewById(R.id.imageButtonDateEnd);
        //Button
        btnCreateTask = (Button) view.findViewById(R.id.buttonCreateTask);
        // Text View
        textViewDate = (TextView) view.findViewById(R.id.textViewContentDate);
        textViewTaskStart = (TextView) view.findViewById(R.id.textViewTasStart);
        textViewTaskEnd = (TextView) view.findViewById(R.id.textviewTaskEnd);
        textViewDateEnd = (TextView) view.findViewById(R.id.textviewDateEnd);
        //Edit Text
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextDes = (EditText) view.findViewById(R.id.edittextDes);
    }
    private void ChonGioTaskStart(){
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int mins = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                calendar.set(0,0,0,i,i1);
                textViewTaskStart.setText(formatter.format(calendar.getTime()));
            }
        },hours,mins,true);
        timePickerDialog.show();
    }
    private void ChonGioTaskEnd(){
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int mins = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                calendar.set(0,0,0,i,i1);
                textViewTaskEnd.setText(formatter.format(calendar.getTime()));
            }
        },hours,mins,true);
        timePickerDialog.show();
    }
    private void ChooseDateStart(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);
        DatePickerDialog timePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(i,i1,i2);
                textViewDate.setText(format.format(calendar.getTime()));
            }
        },year,month,day);
        timePickerDialog.show();
    }
    private void ChooseDateEnd(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);
        DatePickerDialog timePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(i,i1,i2);
                textViewDateEnd.setText(format.format(calendar.getTime()));
            }
        },year,month,day);
        timePickerDialog.show();
    }
}