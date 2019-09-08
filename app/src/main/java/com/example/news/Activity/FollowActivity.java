package com.example.news.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.print.PrinterId;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.Adapter.FoucsAdapter;
import com.example.news.Adapter.FriendAdapter;
import com.example.news.Bean.ContentInfo;
import com.example.news.Bean.HostInfo;
import com.example.news.Bean.MyImageView;
import com.example.news.Bean.foucs_friend;
import com.example.news.Bean.FocusInfo;
import com.example.news.R;
import com.example.news.Utils.FriendHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.news.Activity.LoginActivity.hostID;

public class FollowActivity extends AppCompatActivity {
    private List<FocusInfo> frid = new ArrayList<>();

    private ListView lv;
    private MyImageView fdimage;
    private TextView name;
    private TextView qianming;
    private Button btn_focus;
    private FocusInfo follow_fd;
    private int Focus_boll;
    private TextView focus_tv;
/*    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            lv.setAdapter(new FoucsAdapter(FollowActivity.this,(List<FocusInfo>) msg.obj));
        }
    };*/

    Handler handlerPra = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 0){
                fdimage.setImageURL("http://192.168.43.121:8080/uploadavatar/"+follow_fd.getAvatar());
                name.setText(follow_fd.getNick());
                qianming.setText(follow_fd.getSign());
                Focus_boll = follow_fd.getFocus_bool();
                if(Focus_boll == 0){
                    btn_focus.setVisibility(View.VISIBLE );
                    focus_tv.setVisibility(View.INVISIBLE );
                }


                else{
                    btn_focus.setVisibility(View.VISIBLE );
                    focus_tv.setVisibility(View.VISIBLE);
                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foucs_friend);
        EditText friendID =findViewById(R.id.follow_ed);
        back_MainActivity();

        fdimage = findViewById(R.id.follow_iv_image);
        name = findViewById(R.id.follow_tv_name);
        qianming = findViewById(R.id.follow_tv_qianming);
        btn_focus = findViewById(R.id.follow_btn);
        focus_tv = findViewById(R.id.follow_tv);
        search_fd(friendID);
/*        btn_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        follow_fd(friendID);


    }

    private void follow_fd(final EditText friendID) {
        btn_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int friend_int = Integer.parseInt(friendID.getText().toString());
                //int friend_int  = Integer.parseInt(friendID);
                foucs_friend ffd = new foucs_friend(hostID,friend_int);
                String jsonstr = new Gson().toJson(ffd);

                RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonstr);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.43.121:8080/friendadd")
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String back = response.body().string();
                        if(back.equals("success")){
                            Handler handler=new Handler(Looper.getMainLooper());
                            handler.post(new Runnable(){
                                public void run(){
                                    btn_focus.setVisibility(View.INVISIBLE );
                                    focus_tv.setVisibility(View.VISIBLE );
                                    Toast.makeText(FollowActivity.this,"已关注",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void search_fd(final EditText friendID){
        Button search_btn = findViewById(R.id.follow_search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int friend_int = Integer.parseInt(friendID.getText().toString());
                foucs_friend ffd = new foucs_friend(hostID,friend_int);
                String jsonstr = new Gson().toJson(ffd);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonstr);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.43.121:8080/findfriend")
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json;
                        json = response.body().string();
                        Gson gson = new Gson();
                        Log.v("aaa",json);
                        follow_fd = gson.fromJson(json,FocusInfo.class);
                        Message message = new Message();
                        message.what = 0;
                        handlerPra.sendMessage(message);
                    }
                });
            }
        });
    }


    private void back_MainActivity(){
        ImageView back_Main = findViewById(R.id.follow_back);
        back_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FollowActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
