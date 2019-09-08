package com.example.news.Utils;


import android.util.Log;


import com.example.news.Bean.CommentInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentHttpUtil{

    private String ID;
    private List<CommentInfo> frid;
    public List<CommentInfo> httpGet(int hostID){
        ID = Integer.toString(hostID);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.43.121:8080/mycomment?id="+ID)
                .build();
        Call call = client.newCall(request);

        try{
            Response response = call.execute();

            if (response.isSuccessful()){
                String json = response.body().string();
                Log.v("aaa",json);
                Gson gson = new Gson();

                frid = gson.fromJson(json,new TypeToken<List<CommentInfo>>(){}.getType());
                Log.v("bbb",frid.get(0).getAvatar());
/*                for(CommentInfo frid1 : frid)
                    Log.v("bbb",frid1.toString());*/
            }else{
                Log.v("aaa","error");
            }


        }catch (Exception e){
            Log.v("massageTag",e.getMessage());
        }
        return  frid;
    }

}
