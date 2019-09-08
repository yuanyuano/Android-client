package com.example.news.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news.Bean.MenuList;

import java.util.List;

import com.example.news.R;

public class MenuAdapter extends BaseAdapter {

    private List<MenuList> datas;
    private LayoutInflater inflater;
    private ViewHolder3 viewHolder;

    public MenuAdapter(Context context,List<MenuList> datas){
        this.inflater = LayoutInflater.from(context);
        this.datas = datas;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        ViewHolder3 viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_view_menu, null);
            viewHolder = new ViewHolder3();
            viewHolder.iv_menulist = (ImageView)convertView.findViewById(R.id.iv_menulist);
            viewHolder.tv_menulist = (TextView)convertView.findViewById(R.id.tv_menulist);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder3)convertView.getTag();
        }
        viewHolder.iv_menulist.setBackgroundResource((Integer) datas.get(position).getImage());
        viewHolder.tv_menulist.setText((String)datas.get(position).getMenu_name());
        return convertView;
    }

    private static class ViewHolder3 {
        private ImageView iv_menulist;
        private TextView tv_menulist;
    }
}
