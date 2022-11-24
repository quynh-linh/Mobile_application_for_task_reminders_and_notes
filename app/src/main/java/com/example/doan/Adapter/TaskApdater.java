package com.example.doan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.doan.Model.Task;
import com.example.doan.R;

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

    static class TaskListHolder {
        TextView name,description,date,id;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TaskListHolder holder;
        if(view ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder = new TaskListHolder();
            holder.name = (TextView) view.findViewById(R.id.textViewTitle);
            holder.description = (TextView) view.findViewById(R.id.textViewDes);
            holder.date = (TextView) view.findViewById(R.id.textViewDate);
            holder.id = (TextView) view.findViewById(R.id.textViewId);
            view.setTag(holder);
        } else {
            holder = (TaskListHolder)view.getTag();
        }
        // gán giá trị
        Task task = taskList.get(i);
        holder.name.setText(task.getName());
        holder.description.setText(task.getDescription());
        holder.date.setText(task.getDate() + " " +  task.getTime());
        holder.id.setText(task.getId());
        holder.id.setVisibility(View.INVISIBLE);
        return view;
    }
}
