package com.example.news.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.example.news.Bean.CommentInfo;
import com.example.news.Bean.MyImageView;
import com.example.news.R;

import java.util.List;

public class MyCommentAdapter extends BaseAdapter {

    private List<CommentInfo> mData;
    private LayoutInflater inflater;

    public MyCommentAdapter(Context context, List<CommentInfo> data){
        this.inflater = LayoutInflater.from(context);
        this.mData = data;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        ViewHolder2 viewHolder2 = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.my_comments_item,null);
            viewHolder2 = new ViewHolder2();
            viewHolder2.comment = convertView.findViewById(R.id.tv_comment);
            viewHolder2.name = convertView.findViewById(R.id.tv_name);
            viewHolder2.time = convertView.findViewById(R.id.tv_time);
            viewHolder2.pic = convertView.findViewById(R.id.iv_pic);
            viewHolder2.avatar = convertView.findViewById(R.id.iv_usericon);

            convertView.setTag(viewHolder2);
        }else{
            viewHolder2 = (ViewHolder2)convertView.getTag();
        }
        viewHolder2.pic.setImageURL("http://192.168.43.121:8080/uploadimage/"+mData.get(position).getPic());
        viewHolder2.avatar.setImageURL("http://192.168.43.121:8080/uploadavatar/"+mData.get(position).getAvatar());
        viewHolder2.name.setText(mData.get(position).getName());
        viewHolder2.time.setText(mData.get(position).getTime());
        viewHolder2.comment.setText(mData.get(position).getComment());
        return convertView;
    }

    private static class ViewHolder2{
        private MyImageView avatar;
        private TextView name;
        private TextView comment;
        private TextView time;
        private MyImageView pic;

    }
}

