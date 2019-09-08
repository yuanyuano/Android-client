package com.example.news.Utils;

import android.util.Log;

import com.example.news.Bean.ContentInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyselfUtil {
    private String ID;
    private ContentInfo frid;
    public ContentInfo httpGet(int hostID){
        ID = Integer.toString(hostID);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.43.121:8080/mainhost?id="+ID)
                .build();
        Call call = client.newCall(request);

        try{
            Response response = call.execute();
            String json = "";
            if (response.isSuccessful()){
                json = response.body().string();
            }

            Gson gson = new Gson();
            Log.v("aaa",json);
            frid = gson.fromJson(json,new TypeToken<ContentInfo>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  frid;
    }
}
