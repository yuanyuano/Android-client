package com.example.news.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.news.Bean.ContentInfo;
import com.example.news.Bean.MyImageView;
import com.example.news.R;

import java.util.List;

public class FriendAdapter extends BaseAdapter {
    private List<ContentInfo> mData;
    private LayoutInflater inflater;

    public FriendAdapter(Context context,List<ContentInfo> data){
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
            convertView = inflater.inflate(R.layout.list_view_friend,null);
            viewHolder2 = new ViewHolder2();
            viewHolder2.fdimage = convertView.findViewById(R.id.fd_iv_image);
            viewHolder2.name = convertView.findViewById(R.id.fd_tv_name);
            viewHolder2.qianming = convertView.findViewById(R.id.fd_tv_qianming);
            convertView.setTag(viewHolder2);
        }else{
            viewHolder2 = (ViewHolder2)convertView.getTag();
        }
        viewHolder2.fdimage.setImageURL("http://192.168.43.121:8080/uploadavatar/"+mData.get(position).getAvatar());
        viewHolder2.name.setText(mData.get(position).getNick());
        viewHolder2.qianming.setText(mData.get(position).getSign());
        return convertView;
    }

    private static class ViewHolder2{
        private MyImageView fdimage;
        private TextView name;
        private TextView qianming;
    }
}
