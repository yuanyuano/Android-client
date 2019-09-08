package com.example.news.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.news.R;

import java.util.List;
import java.util.Map;

public class ListViewAdapter extends BaseAdapter {
    private List<Map<String,Object>> mData;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public ListViewAdapter(Context context,List<Map<String,Object>> data){
        this.mContext = context;
        this.mData = data;
        this.layoutInflater = LayoutInflater.from(context);
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
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_view_weibo,null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView)convertView.findViewById(R.id.iv_image);
            viewHolder.user = (TextView)convertView.findViewById(R.id.tv_user);
            viewHolder.info = (TextView)convertView.findViewById(R.id.tv_info);
            viewHolder.time = (TextView)convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.user.setBackgroundResource((Integer) mData.get(position).get("image"));
        viewHolder.user.setText((String)mData.get(position).get("user"));
        viewHolder.user.setText((String)mData.get(position).get("info"));
        viewHolder.user.setText((String)mData.get(position).get("time"));
        return convertView;
    }

    private static class ViewHolder{
        private ImageView image;
        private TextView user;
        private TextView info;
        private TextView time;
    }
}
