package com.example.news.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.news.Bean.Comment;
import com.example.news.Bean.MyImageView;
import com.example.news.R;

import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private List<Comment> datas;
    private LayoutInflater inflater;
    private CommentAdapter.ViewHolder viewHolder;

    public CommentAdapter() {

    }

    public void setDatas(List<Comment> datas) {
        this.datas = datas;
    }

    public void setInflater(Context context) {
        this.inflater = LayoutInflater.from(context);
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        viewHolder = new CommentAdapter.ViewHolder();
        if(view == null ){
            view = inflater.inflate(R.layout.comment_item, null);
            viewHolder.img_avatar = view.findViewById(R.id.comment_img_avatar);
            viewHolder.tv_name = view.findViewById(R.id.comment_tv_name);
            viewHolder.tv_content = view.findViewById(R.id.comment_tv_content);
            viewHolder.tv_time = view.findViewById(R.id.comment_tv_time);

            view.setTag(viewHolder);
        }else {
            viewHolder = (CommentAdapter.ViewHolder)view.getTag();
        }
        viewHolder.img_avatar.setImageURL("http://192.168.43.121:8080/uploadavatar/"+datas.get(i).getHostAvatar());
        viewHolder.tv_name.setText(datas.get(i).getHostNick());
        viewHolder.tv_time.setText(datas.get(i).getCreateTime());
        viewHolder.tv_content.setText(datas.get(i).getCom_textField());
        return view;
    }

    public static class ViewHolder{
        public MyImageView img_avatar;
        public TextView tv_name,tv_content,tv_time;
    }

}

