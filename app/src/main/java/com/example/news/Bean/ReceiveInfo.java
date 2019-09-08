package com.example.news.Bean;
import android.util.Log;

import java.net.URLDecoder;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ReceiveInfo {


    private int ID;
    List<Dynamic> dy;
    List<Comment> com;
    PraiseDetail pra;
    public List<Dynamic> ReiceiveDynamic(int hostID) {

            ID = hostID;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()//get
                    .url("http://192.168.43.121:8080/all?id="+ID)
                    .build();
            Call call = client.newCall(request);
            try {
                Response response = call.execute();
                    String json = response.body().string();
                Log.v("1234",json);
                    Gson gson = new Gson();
                    dy = gson.fromJson(json, new TypeToken<List<Dynamic>>() {}.getType());
                    for(Dynamic dy1 : dy)
                        Log.v("bbb",dy1.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            return dy;
    }

    public List<Dynamic> ReiceiveCollect(int hostID) {

        ID = hostID;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()//get
                .url("http://192.168.43.121:8080/mycollect?id="+ID)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String json = response.body().string();
            Log.v("1234",json);
            Gson gson = new Gson();
            dy = gson.fromJson(json, new TypeToken<List<Dynamic>>() {}.getType());
            for(Dynamic dy1 : dy)
                Log.v("bbb1",dy1.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return dy;
    }

    public List<Comment> ReiceiveComment(int DynamicID) {

        ID = DynamicID;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()//get
                .url("http://192.168.43.121:8080/comment?id="+ID)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String json = URLDecoder.decode(response.body().string(),"utf-8");
            Log.v("aaa",json);
            Gson gson = new Gson();
            com = gson.fromJson(json, new TypeToken<List<Comment>>() {}.getType());
/*            for(Dynamic dy1 : dy)
                Log.v("bbb",dy1.toString());*/
        }catch (Exception e){
            e.printStackTrace();
        }
        return com;
    }

    public List<Dynamic> ReiceiveMoment(int hostID) {

        ID = hostID;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()//get
                .url("http://192.168.43.121:8080/mymoment?id="+ID)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String json = response.body().string();
            Log.v("1234",json);
            Gson gson = new Gson();
            dy = gson.fromJson(json, new TypeToken<List<Dynamic>>() {}.getType());
            for(Dynamic dy1 : dy)
                Log.v("bbb1",dy1.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return dy;
    }


}
