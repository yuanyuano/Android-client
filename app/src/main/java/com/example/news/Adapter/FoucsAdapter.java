package com.example.news.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.news.Bean.ContentInfo;
import com.example.news.Bean.FocusInfo;
import com.example.news.Bean.MyImageView;
import com.example.news.R;

import java.util.List;

public class FoucsAdapter extends BaseAdapter {

    private List<FocusInfo> mData;
    private LayoutInflater inflater;

    public FoucsAdapter(Context context, List<FocusInfo> data){
        this.inflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder4 viewHolder2 = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_view_follow,null);
            viewHolder2 = new ViewHolder4();
            viewHolder2.fdimage = convertView.findViewById(R.id.follow_iv_image);
            viewHolder2.name = convertView.findViewById(R.id.follow_tv_name);
            viewHolder2.qianming = convertView.findViewById(R.id.follow_tv_qianming);
            convertView.setTag(viewHolder2);
        }else{
            viewHolder2 = (ViewHolder4)convertView.getTag();
        }
        viewHolder2.fdimage.setImageURL("http://192.168.43.121:8080/uploadavatar/"+mData.get(position).getAvatar());
        viewHolder2.name.setText(mData.get(position).getNick());
        viewHolder2.qianming.setText(mData.get(position).getSign());
        return convertView;
    }
    private static class ViewHolder4{
        private MyImageView fdimage;
        private TextView name;
        private TextView qianming;
    }
}
