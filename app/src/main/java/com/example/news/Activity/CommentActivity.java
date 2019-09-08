package com.example.news.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.example.news.Adapter.MyCommentAdapter;
import com.example.news.Bean.CommentInfo;
import com.example.news.R;
import com.example.news.Utils.CommentHttpUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.news.Activity.LoginActivity.hostID;

public class CommentActivity extends AppCompatActivity {
    private ListView lv;
    List<CommentInfo> com_list = new ArrayList<>();
    MyCommentAdapter comAdapter;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            lv.setAdapter(new MyCommentAdapter(CommentActivity.this,(List<CommentInfo>) msg.obj));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_comments);
        lv = findViewById(R.id.lv_comments);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CommentHttpUtil rec = new CommentHttpUtil();
                    com_list = rec.httpGet(hostID);
                    Log.v("aaa","11111");
                    if (com_list!=null)
                        handler.sendMessage(handler.obtainMessage(22,com_list));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
