package com.example.news.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.news.Adapter.FriendAdapter;
import com.example.news.Bean.ContentInfo;
import com.example.news.R;
import com.example.news.Utils.FriendHttpUtil;

import java.util.List;

import static com.example.news.Activity.LoginActivity.hostID;


public class FriendFragment extends Fragment{
    private ListView lv;
    public FriendFragment() {
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
                lv.setAdapter(new FriendAdapter(getActivity(),(List<ContentInfo>) msg.obj));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friend,null);
        lv = view.findViewById(R.id.fd_listview);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FriendHttpUtil rec = new FriendHttpUtil();
                    List<ContentInfo> list_item = rec.httpGet(hostID);
                    if (list_item!=null)
                        handler.sendMessage(handler.obtainMessage(22,list_item));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        return view;
    }
}
