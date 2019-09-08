package com.example.news.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.news.Bean.HostInfo;
import com.example.news.R;
import com.example.news.Utils.AlbumUtil;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.framed.FrameReader;

public class RegisterActivity extends Activity {
    private ImageView back,logo;
    private EditText username, userid, userpassword, repassword;
    private Button regist;
    protected static Uri temUri;
    private Bitmap photo;
    private String id_str;
    private CheckBox check;
    private List<LocalMedia> selectList = new ArrayList<>();
    String path="";
    //private GridImageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        logo = (ImageView) findViewById(R.id.r_logo);
        username =(EditText) findViewById(R.id.register_username);
        userid =(EditText) findViewById(R.id.register_id);
        userpassword =(EditText) findViewById(R.id.register_password);
        repassword =(EditText) findViewById(R.id.register_repassword);
        regist =(Button) findViewById(R.id.regist);
        back = (ImageView) findViewById(R.id.back);
        check = (CheckBox) findViewById(R.id.check_box);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBox();
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent nextIntent = new Intent(RegisterActivity.this, LoginRegisterActivity.class);
                startActivity(nextIntent);
                finish();
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.isChecked()){
                    String username_str = username.getText().toString();
                    id_str = userid.getText().toString();
                    int userid_str = Integer.parseInt(userid.getText().toString());
                    String userpassword_str = userpassword.getText().toString();
                    String repassword_str = repassword.getText().toString();
                    String imagePath = AlbumUtil.savePhoto(photo, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));

                    if (userpassword_str.equals(repassword_str)) {
                        HostInfo hostInfo = new HostInfo(userid_str,username_str,null,userpassword_str);
                        String jsonstr = new Gson().toJson(hostInfo);
                        System.out.println(jsonstr);

                        uploadPic(path);

                        RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonstr);
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url("http://192.168.43.121:8080/insert").post(body).build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.v("call","fail");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String back = response.body().string();
                                if(back.equals("success")){
                                    Handler handler=new Handler(Looper.getMainLooper());
                                    handler.post(new Runnable(){
                                        public void run(){
                                            Toast.makeText(RegisterActivity.this,"注册成功，请重新登录！",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Intent nextIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(nextIntent);
                                    finish();
                                }else if(back.equals("fail")){
                                    Handler handler=new Handler(Looper.getMainLooper());
                                    handler.post(new Runnable(){
                                        public void run(){
                                            Toast.makeText(RegisterActivity.this,"注册失败，请重试！",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        Handler handler=new Handler(Looper.getMainLooper());
                        handler.post(new Runnable(){
                            public void run(){
                                Toast.makeText(RegisterActivity.this, "两次密码不一致，请重新输入！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else{
                    Handler handler=new Handler(Looper.getMainLooper());
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(RegisterActivity.this, "请勾选使用条款！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    protected void showDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        setTitle("设置头像");
        String[] items = {"拍照","选择本地照片"};
        builder.setNegativeButton("取消",null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0://拍照
                        /*if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.CAMERA},0);
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            File uriFile = new File(AlbumUtil.getPath(RegisterActivity.this, temUri));
                            temUri = FileProvider.getUriForFile(RegisterActivity.this, getPackageName(),uriFile);
                        } else
                            temUri = Uri.fromFile(new File(Environment.getDownloadCacheDirectory(),"image.jpg"));
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,temUri);
                        startActivityForResult(openCameraIntent,0);*/
                        PictureSelector.create(RegisterActivity.this)
                                .openCamera(PictureMimeType.ofImage())
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case 1://选择本地图片
                        /*if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent,1);*/
                        PictureSelector.create(RegisterActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(1)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .enableCrop(true)
                                .withAspectRatio(1, 1)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {

                images = PictureSelector.obtainMultipleResult(data);
                selectList.addAll(images);
                LocalMedia media = images.get(0);
                if (media.isCut() && !media.isCompressed())
                    path = media.getCutPath();

                Bitmap bitmap = BitmapFactory.decodeFile(path);
                logo.setImageBitmap(bitmap);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode== 0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Handler handler=new Handler(Looper.getMainLooper());
                handler.post(new Runnable(){
                    public void run(){
                        Toast.makeText(RegisterActivity.this,"授权成功，请再次选择操作！", Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Handler handler=new Handler(Looper.getMainLooper());
                handler.post(new Runnable(){
                    public void run(){
                        Toast.makeText(RegisterActivity.this,"请给APP授权，否则无法正常使用！",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void uploadPic(String imagePath){

        if(imagePath != null){
            Log.v("shuangchuan",imagePath);
            File file = new File(imagePath);
            MultipartBody body = new MultipartBody.Builder().addFormDataPart("hostID",id_str).addFormDataPart("file",id_str+".jpg",RequestBody.create(MediaType.parse("image/jpg"),file)).build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://192.168.43.121:8080/uploadavatar").post(body).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.v("uploadhead","fail");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String back = response.body().string();
                    if(back.equals("success")){
                        Handler handler=new Handler(Looper.getMainLooper());
                        handler.post(new Runnable(){
                            public void run(){
                                Toast.makeText(RegisterActivity.this,"头像上传成功！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if(back.equals("fail")){
                        Handler handler=new Handler(Looper.getMainLooper());
                        handler.post(new Runnable(){
                            public void run(){
                                Toast.makeText(RegisterActivity.this,"头像上传失败，请重试！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }
}
