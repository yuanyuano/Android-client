package com.example.news.Utils;

import android.util.Log;

import com.example.news.Bean.ContentInfo;
import com.example.news.Bean.foucs_friend;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FollowUtil  {
    private int hostID;
    private List<ContentInfo> frid;

    public List<ContentInfo> FollowGet(String friendID){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.43.121:8080/friend?id="+friendID)
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
            frid = gson.fromJson(json,new TypeToken<List<ContentInfo>>(){}.getType());
            for(ContentInfo frid1 : frid)
                Log.v("bbb",frid1.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  frid;
    }
}
