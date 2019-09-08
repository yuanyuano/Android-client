package com.example.news.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.Bean.Dynamic;
import com.example.news.Bean.MyImageView;
import com.example.news.NineGrid.NineGridTestLayout;
import com.example.news.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAdapter extends BaseAdapter {

    private List<Dynamic> datas;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;

    public ViewAdapter() {
    }

    public ViewAdapter(List<Dynamic> datas, Context context) {
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    public void setDatas(List<Dynamic> datas) {
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
        viewHolder = new ViewHolder();
        if(view == null ){
            view = inflater.inflate(R.layout.view_item, null);
            viewHolder.img_avatar = view.findViewById(R.id.img_avatar);
            viewHolder.tv_name = view.findViewById(R.id.tv_name);
            viewHolder.tv_content = view.findViewById(R.id.tv_content);
            viewHolder.mImageLayout = view.findViewById(R.id.img_image);
            viewHolder.tv_time = view.findViewById(R.id.tv_time);
            viewHolder.img_shoucang = view.findViewById(R.id.img_shoucang);
            viewHolder.img_pinglun = view.findViewById(R.id.img_pinglun);
            viewHolder.img_dianzan = view.findViewById(R.id.img_dianzan);
            viewHolder.tv_num_dianzan = view.findViewById(R.id.tv_num_dianzan);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.img_avatar.setImageURL("http://192.168.43.121:8080/uploadavatar/"+datas.get(i).getHostAvatar());
        viewHolder.tv_name.setText(datas.get(i).getHostNick());
        viewHolder.tv_time.setText(datas.get(i).getCreateTime());
        viewHolder.tv_content.setText(datas.get(i).getMom_textField());
        viewHolder.tv_num_dianzan.setText(Integer.toString(datas.get(i).getHasPraised()));

        viewHolder.mImageLayout.setIsShowAll(false);
        viewHolder.mImageLayout.setSpacing(5);

        if(datas.get(i).getIsCollected() ==1)
            viewHolder.img_shoucang.setSelected(true);
        else
            viewHolder.img_shoucang.setSelected(false);

        if(datas.get(i).getIsPraised() ==1)
            viewHolder.img_dianzan.setSelected(true);
        else
            viewHolder.img_dianzan.setSelected(false);

        String sImage = datas.get(i).getMom_image();
        String image_item;
        List<String> image_list = new ArrayList<>();
        int p=0;
        for(int k=0; k<sImage.length();k++){
            if(sImage.charAt(k) == '|'){
                image_item = "http://192.168.43.121:8080/uploadimage/"+sImage.substring(p,k);
                //image_item = sImage.substring(p,k);
                Log.v("Myimage2",image_item);
                p=k+1;
                image_list.add(image_item);
            }
        }
        if(image_list.size() == 0){
            Log.v("Myimage1",sImage);
            image_list.add("http://192.168.43.121:8080/uploadimage/"+sImage);
        }

        else{
            image_item = sImage.substring(p,sImage.length());
            image_list.add("http://192.168.43.121:8080/uploadimage/"+image_item);
            Log.v("Myimage2",image_item);
        }
        viewHolder.mImageLayout.setUrlList(image_list);

        viewHolder.img_shoucang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mOnItemCollectListener.onCollectClick(i);
            }
        });
        viewHolder.img_pinglun.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mOnItemCommentListener.onCommentClick(i);
            }
        });
        viewHolder.img_dianzan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mOnItemPraiseListener.onPraiseClick(i);
            }
        });

        return view;
    }

    public static class ViewHolder{
        public MyImageView img_avatar;
        public TextView tv_name,tv_content,tv_time,tv_num_dianzan;
        public NineGridTestLayout mImageLayout;
        public ImageView img_shoucang,img_dianzan,img_pinglun;
    }

    public interface onItemCollectListener{
        void onCollectClick(int i);
    }

    public interface onItemCommentListener{
        void onCommentClick(int i);
    }

    public interface onItemPraiseListener{
        void onPraiseClick(int i);
    }

    private onItemCollectListener mOnItemCollectListener;
    private onItemCommentListener mOnItemCommentListener;
    private onItemPraiseListener mOnItemPraiseListener;

    public void setOnItemCollectListener(onItemCollectListener mOnItemCollectListener) {
        this.mOnItemCollectListener = mOnItemCollectListener;
    }

    public void setOnItemCommentClickListener(onItemCommentListener mOnItemCommentListener) {
        this.mOnItemCommentListener = mOnItemCommentListener;
    }
    public void setOnItemPraiseClickListener(onItemPraiseListener mOnItemPraiseListener) {
        this.mOnItemPraiseListener = mOnItemPraiseListener;
    }
}
