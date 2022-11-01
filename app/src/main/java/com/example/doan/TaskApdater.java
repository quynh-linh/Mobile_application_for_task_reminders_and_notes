package com.example.doan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class TaskApdater extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Task> taskList;

    public TaskApdater(Context context, int layout, List<Task> taskList) {
        this.context = context;
        this.layout = layout;
        this.taskList = taskList;
    }
    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        //
        TextView time = (TextView) view.findViewById(R.id.textViewTime);
        TextView name = (TextView) view.findViewById(R.id.textviewName);
        TextView description = (TextView) view.findViewById(R.id.textviewDess);
        TextView date = (TextView) view.findViewById(R.id.textViewDate);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBoxStatus);
        // gán giá trị
        Task task = taskList.get(i);
        time.setText(task.getTime());
        name.setText(task.getName());
        description.setText(task.getDescription());
        date.setText(task.getDate());
        if (task.getCheck_status().equals("1")){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }
        return view;
    }
}
