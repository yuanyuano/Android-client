package com.example.news.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.example.news.Adapter.LikeAdapter;
import com.example.news.Bean.LikeInfo;
import com.example.news.R;
import com.example.news.Utils.LikeHttpUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.news.Activity.LoginActivity.hostID;

public class MyLikeActivity extends AppCompatActivity {
    private ListView lv;
    List<LikeInfo> like_list = new ArrayList<>();
    LikeAdapter likeAdapter;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            lv.setAdapter(new LikeAdapter(MyLikeActivity.this,(List<LikeInfo>) msg.obj));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_likes);
        lv = findViewById(R.id.lv_likes);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LikeHttpUtil rec = new LikeHttpUtil();
                    like_list = rec.httpGet(hostID);
                    if (like_list!=null)
                        handler.sendMessage(handler.obtainMessage(22,like_list));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
