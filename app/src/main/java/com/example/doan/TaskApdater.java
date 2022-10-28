package com.example.doan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        TextView textviewTimeStart = (TextView) view.findViewById(R.id.timeStarts);
        TextView textviewTimeEnd = (TextView) view.findViewById(R.id.timeEnd);
        TextView name = (TextView) view.findViewById(R.id.textviewName);
        TextView description = (TextView) view.findViewById(R.id.textviewDess);
        TextView dateStarts = (TextView) view.findViewById(R.id.dateStarts);
        TextView endStarts = (TextView) view.findViewById(R.id.dateEnd);
        // gán giá trị
        Task task = taskList.get(i);
        textviewTimeStart.setText(task.getTimeStart());
        name.setText(task.getName());
        description.setText(task.getDescription());
        textviewTimeEnd.setText(task.getTimeEnd());
        dateStarts.setText(task.getStartTask());
        endStarts.setText(task.getEndTask());
        return view;
    }
}
