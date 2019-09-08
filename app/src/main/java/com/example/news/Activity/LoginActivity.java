package com.example.news.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.news.Bean.HostInfo;
import com.example.news.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private Button check_user;
    private EditText id, password;
    private ImageView l_back;
    public static int hostID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        check_user=(Button)findViewById(R.id.check_user);
        id=(EditText)findViewById(R.id.login_id);
        password=(EditText)findViewById(R.id.login_password);
        String password_str = password.getText().toString();
        l_back = (ImageView) findViewById(R.id.l_back);

        l_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,LoginRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        check_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hostID = Integer.parseInt(id.getText().toString());
                String password_str = password.getText().toString();
                HostInfo hostInfo = new HostInfo(hostID,null,null,password_str);
                String jsonstr = new Gson().toJson(hostInfo);

                RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonstr);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://192.168.43.121:8080/login").post(body).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String back = response.body().string();

                        if(back.equals("success")){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            //finish();
                        }else if(back.equals("fail")){
                            Handler handler=new Handler(Looper.getMainLooper());
                            handler.post(new Runnable(){
                                public void run(){
                                    Toast.makeText(LoginActivity.this,"手机号码或密码错误，请重新输入！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
