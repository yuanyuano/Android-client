package com.example.news.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.news.Adapter.CommentAdapter;
import com.example.news.Bean.Comment;
import com.example.news.Bean.MyImageView;
import com.example.news.Bean.SubmitComment;
import com.example.news.NineGrid.MyScrollView;
import com.example.news.NineGrid.NineGridTestLayout;
import com.example.news.NineGrid.UnScrollListView;
import com.example.news.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.news.Bean.Dynamic;
import com.example.news.Bean.ReceiveInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.news.Activity.LoginActivity.hostID;

public class CommentDetail extends AppCompatActivity {
    List<Comment> mComment;
    UnScrollListView lv_comment;
    MyScrollView scroll;
    EditText et_comment;
    TextView tv_submit;
    Dynamic recDy;
    SubmitComment sub = new SubmitComment();
    CommentAdapter adapter = new CommentAdapter();

    private MyImageView img_avatar;
    private TextView tv_name,tv_content,tv_time;
    private NineGridTestLayout mImageLayout;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            adapter.setDatas((List<Comment>)msg.obj);
            adapter.setInflater(CommentDetail.this);
            lv_comment.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);

        Intent it = getIntent();
        recDy = (Dynamic)it.getSerializableExtra("mDynamic");

        img_avatar = findViewById(R.id.mom_img_avatar);
        tv_name = findViewById(R.id.mom_tv_name);
        tv_content = findViewById(R.id.mom_tv_content);
        mImageLayout = findViewById(R.id.mom_img_image);
        tv_time = findViewById(R.id.mom_tv_time);

        img_avatar.setImageURL("http://192.168.43.121:8080/uploadavatar/"+recDy.getHostAvatar());
        tv_name.setText(recDy.getHostNick());
        tv_time.setText(recDy.getCreateTime());
        tv_content.setText(recDy.getMom_textField());

        mImageLayout.setIsShowAll(false);
        mImageLayout.setSpacing(5);
        String sImage = recDy.getMom_image();
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
        mImageLayout.setUrlList(image_list);

        lv_comment = findViewById(R.id.lv_comment);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    ReceiveInfo rec = new ReceiveInfo();
                    mComment = rec.ReiceiveComment(recDy.getDynamicID());
                    handler.sendMessage(handler.obtainMessage(22,mComment));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


        et_comment = findViewById(R.id.et_comment);
        tv_submit = findViewById(R.id.btn_submit);

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String DateTime = simpleDateFormat.format(date);
                sub.setTime(DateTime);
                sub.setHostID(hostID);
                sub.setDynamicID(recDy.getDynamicID());
                sub.setCom_content(et_comment.getText().toString());
                String jsonstr = new Gson().toJson(sub);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonstr);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.43.121:8080/commentadd")
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    ReceiveInfo rec = new ReceiveInfo();
                                    mComment = rec.ReiceiveComment(recDy.getDynamicID());
                                    handler.sendMessage(handler.obtainMessage(22,mComment));
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });

            }
        });






    }
}
