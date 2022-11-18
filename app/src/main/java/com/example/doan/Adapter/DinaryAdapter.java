package com.example.doan.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.doan.Model.DinaryModel;
import com.example.doan.R;
import com.example.doan.Retrofit2.APIUtils;
import com.example.doan.Retrofit2.DataCilent;
import com.example.doan.fragments.LibFragment;
import com.example.doan.fragments.UpdatePostFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DinaryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DinaryModel> arrModels;
    private int layout;
    private FragmentActivity fragmentActivity;
    public DinaryAdapter(Context context, ArrayList<DinaryModel> models, int layout) {
        this.context = context;
        this.arrModels = models;
        this.layout = layout;
        this.fragmentActivity = (FragmentActivity) context;
    }
    @Override
    public int getCount() {
        return arrModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class Holder{
        TextView textViewTitlePost , textViewDatePost , textViewDesPost , textViewIdPost ;
        ImageButton imageButtonInfoPost;
        ImageView imageViewPost;
        Button buttonLike , buttonComment , buttonShare;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder = new Holder();
            holder.textViewTitlePost = (TextView) convertView.findViewById(R.id.textViewTitlePost);
            holder.textViewDatePost = (TextView) convertView.findViewById(R.id.textViewDatePost);
            holder.textViewDesPost = (TextView) convertView.findViewById(R.id.textViewDesPost);
            holder.textViewIdPost = (TextView) convertView.findViewById(R.id.textViewIdPost);
            holder.imageViewPost = (ImageView) convertView.findViewById(R.id.imageViewPost);
            holder.imageButtonInfoPost = (ImageButton)  convertView.findViewById(R.id.imageButtonInfoPost);
            holder.buttonLike = (Button)  convertView.findViewById(R.id.buttonLikePost);
            holder.buttonComment = (Button)  convertView.findViewById(R.id.buttonCommentPost);
            holder.buttonShare = (Button)  convertView.findViewById(R.id.buttonSharePost);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        DinaryModel dinaryModel = arrModels.get(i);
        holder.textViewTitlePost.setText(dinaryModel.getTitle());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String date = dinaryModel.getDate();
        Date date1 = new Date(date);
        String time = simpleDateFormat.format(date1);
        String status = "";
        String find_id = dinaryModel.getFind_id();
        if (find_id.trim().equals("1")){
            status = "Vui";
        } else if (find_id.trim().equals("2")){
            status = "Buồn";
        } else if (find_id.trim().equals("3")){
            status = "Tức giận";
        }else if (find_id.trim().equals("4")){
            status = "Hạnh phúc";
        }

        holder.textViewDatePost.setText(time+ " " + " đang cảm thấy " + status);
        holder.textViewDesPost.setText(dinaryModel.getContent());
        holder.textViewIdPost.setText(dinaryModel.getUser_id());
        holder.textViewIdPost.setVisibility(View.INVISIBLE);
        Glide.with(holder.imageViewPost.getContext()).load(APIUtils.baseUrl+"image/"+dinaryModel.getImg()).into(holder.imageViewPost);
        int id = Integer.valueOf(dinaryModel.getId());
        holder.imageButtonInfoPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseInfoPost(holder.imageButtonInfoPost,id);
            }
        });
        return convertView;
    }
    public void ChooseInfoPost(ImageButton button , int id){
        PopupMenu popupMenu = new PopupMenu(context,button);
        popupMenu.getMenuInflater().inflate(R.menu.menu_choose_infopost,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.edit:
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",id);
                        UpdatePostFragment frag = new UpdatePostFragment();
                        frag.setArguments(bundle);
                        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.constraint, frag);
                        transaction.commit();
                        break;
                    case R.id.remove:
                        RemovePost(id);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }
                return false;
            }
        });
        popupMenu.show();
    }
    public void RemovePost(int id){
        DataCilent dataCilent =APIUtils.getData();
        Call<String> call1 = dataCilent.removeDataPost(id);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                if (result.trim().equals("success")){
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    LibFragment libFragment = new LibFragment();
                    DinaryAdapter dinaryAdapter = libFragment.dinaryAdapter;
                    dinaryAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error add", t.getMessage().toString());
            }
        });
    }
}
