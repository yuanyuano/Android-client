package com.example.news.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.news.Bean.LikeInfo;
import com.example.news.Bean.MyImageView;
import com.example.news.R;

import java.util.List;

public class LikeAdapter extends BaseAdapter {

    private List<LikeInfo> mData;
    private LayoutInflater inflater;

    public LikeAdapter(Context context, List<LikeInfo> data){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder2 viewHolder2 = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.me_likes_item,null);
            viewHolder2 = new ViewHolder2();
            viewHolder2.name = convertView.findViewById(R.id.tv_name_like);
            viewHolder2.time = convertView.findViewById(R.id.tv_time_like);
            viewHolder2.pic = convertView.findViewById(R.id.iv_pic_like);
            viewHolder2.avatar = convertView.findViewById(R.id.iv_usericon_like);

            convertView.setTag(viewHolder2);
        }else{
            viewHolder2 = (ViewHolder2)convertView.getTag();
        }
        viewHolder2.pic.setImageURL("http://192.168.43.121:8080/uploadimage/"+mData.get(position).getPic());
        viewHolder2.avatar.setImageURL("http://192.168.43.121:8080/uploadavatar/"+mData.get(position).getAvatar());
        viewHolder2.name.setText(mData.get(position).getName());
        viewHolder2.time.setText(mData.get(position).getTime());
        return convertView;
    }

    private static class ViewHolder2{
        private MyImageView avatar;
        private TextView name;
        private TextView time;
        private MyImageView pic;

    }
}