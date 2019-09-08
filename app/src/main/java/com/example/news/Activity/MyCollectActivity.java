package com.example.news.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.news.Activity.CommentDetail;
import com.example.news.Adapter.ViewAdapter;
import com.example.news.Bean.Dynamic;
import com.example.news.Bean.PraiseDetail;
import com.example.news.Bean.PraiseOrCollectMsg;
import com.example.news.Bean.ReceiveInfo;
import com.example.news.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.news.Activity.LoginActivity.hostID;


public class MyCollectActivity extends AppCompatActivity {

    ListView lv_view;
    List<Dynamic> list_item;
    ViewAdapter adapter = new ViewAdapter();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){

            adapter.setDatas((List<Dynamic>)msg.obj);
            adapter.setInflater(MyCollectActivity.this);
            if(adapter == null) Log.v("emptyAdapter","empty");
            lv_view.setAdapter(adapter);

            adapter.setOnItemCollectListener(new ViewAdapter.onItemCollectListener() {
                @Override
                public void onCollectClick(final int i) {
                    PraiseOrCollectMsg msg = new PraiseOrCollectMsg();
                    msg.setDynamicID(list_item.get(i).getDynamicID());
                    msg.setHostID(1);
                    String jsonstr = new Gson().toJson(msg);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonstr);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.43.121:8080/findcollect")
                            .post(body)
                            .build();
                    client.newCall(request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String json = response.body().string();
                            Log.v("accepet",json);
                            list_item.get(i).setIsCollected(Integer.parseInt(json));
                            //adapter.notifyDataSetChanged();
                            Message message = new Message();
                            message.what = 0;
                            handlerPra.sendMessage(message);
                        }
                    });

                }
            });

            adapter.setOnItemCommentClickListener(new ViewAdapter.onItemCommentListener() {
                @Override
                public void onCommentClick(int i) {
                    comment(i);
                }
            });

            adapter.setOnItemPraiseClickListener(new ViewAdapter.onItemPraiseListener() {
                @Override
                public void onPraiseClick(final int i) {
                    PraiseOrCollectMsg msg = new PraiseOrCollectMsg();
                    msg.setDynamicID(list_item.get(i).getDynamicID());
                    msg.setHostID(hostID);
                    String jsonstr = new Gson().toJson(msg);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonstr);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.43.121:8080/findpraise")
                            .post(body)
                            .build();
                    client.newCall(request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String json = response.body().string();
                            Gson gson = new Gson();
                            PraiseDetail pra = gson.fromJson(json,PraiseDetail.class);
                            list_item.get(i).setHasPraised(pra.haspriased);
                            list_item.get(i).setIsPraised(pra.isprased);
                            //adapter.notifyDataSetChanged();
                            Message message = new Message();
                            message.what = 0;
                            handlerPra.sendMessage(message);
                        }
                    });

                }
            });
        }
    };

    Handler handlerPra = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 0){
                adapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collect);
        lv_view = findViewById(R.id.lv_view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    ReceiveInfo rec = new ReceiveInfo();//Log.v("1234","1234");
                    list_item = rec.ReiceiveCollect(1);
                    handler.sendMessage(handler.obtainMessage(22,list_item));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void comment(int i){
        Dynamic dy = list_item.get(i);
        Intent commentDetail = new Intent(MyCollectActivity.this, CommentDetail.class);
        commentDetail.putExtra("mDynamic",dy);
        startActivity(commentDetail);
    }

    private void back_MainActivity(){
        ImageView back_Main = findViewById(R.id.iv_menu);
        back_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCollectActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
