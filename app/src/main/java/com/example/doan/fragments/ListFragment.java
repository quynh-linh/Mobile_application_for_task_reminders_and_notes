package com.example.doan.fragments;

import static android.graphics.Color.rgb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.doan.R;
import com.example.doan.Task;
import com.example.doan.TaskApdater;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class ListFragment extends Fragment {
    ListView listView;
    TaskApdater taskApdater;
    ArrayList<Task> arrayListTasks;
    ImageButton btn_create;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view=  inflater.inflate(R.layout.fragment_list, container, false);
        AnhXa(view);
        Bundle bundle = getArguments();
        if (bundle!=null){
//            Random rnd = new Random();
//            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//            (R.layout.row_listview_task).setBackgroundColor(color);
            String name = bundle.getString("name");
            String description = bundle.getString("description");
            String dateStart = bundle.getString("dateStart");
            String dateEnd = bundle.getString("dateEnd");
            String timeStart = bundle.getString("timeStart");
            String timeEnd = bundle.getString("timeEnd");
            Task task = new Task(name,description,dateStart,dateEnd,timeStart,timeEnd);
            arrayListTasks.add(task);
            taskApdater = new TaskApdater(getActivity(),R.layout.row_listview_task,arrayListTasks);
            listView.setAdapter(taskApdater);
        }
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
        return view;
    }
    private void AnhXa(View view){
        btn_create = (ImageButton) view.findViewById(R.id.btn_create);
        listView = (ListView) view.findViewById(R.id.listViewTask);
        arrayListTasks = new ArrayList<>();
    }
}