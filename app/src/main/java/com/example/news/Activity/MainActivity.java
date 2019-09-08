package com.example.news.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.Bean.ContentInfo;
import com.example.news.Bean.MyImageView;
import com.example.news.Bean.ReceiveInfo;
import com.example.news.Fragment.ChannelFragment;
import com.example.news.Fragment.AnnounceFragment;
import com.example.news.Fragment.FriendFragment;
import com.example.news.R;
import com.example.news.Utils.MyselfUtil;
import com.google.android.material.navigation.NavigationView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import static com.example.news.Activity.LoginActivity.hostID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //UI Object
    private TextView txt_topbar;
    private TextView txt_channel;
    private TextView txt_message;
    private TextView txt_setting;
    private FrameLayout ly_content;

    //Fragment Object
    private ChannelFragment channelF;
    private AnnounceFragment announceF;
    private FriendFragment settingF;
    private FragmentManager fManager;

    //侧滑菜单栏
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    ImageView menu;

    private MyImageView user_image;
    private TextView user_name;
    private TextView user_sign;
    private ContentInfo MyInfo;


    Handler handlerPra = new Handler(){
        @Override
        public void handleMessage(Message msg){
                MyInfo = (ContentInfo) msg.obj;

                user_image.setImageURL("http://192.168.43.121:8080/uploadavatar/"+MyInfo.getAvatar());
                user_name.setText(MyInfo.getNick());
                user_sign.setText(MyInfo.getSign());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        fManager = getSupportFragmentManager();
        bindViews();
        txt_channel.performClick();

        initView();
        follow_fd();

    }

    //关注好友
    private void follow_fd(){
        ImageView follow_fd = (ImageView)findViewById(R.id.iv_follow_fd);
        follow_fd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FollowActivity.class);
                startActivity(intent);
            }
        });
    }

    //侧滑菜单
    private void initView(){
        drawerLayout = findViewById(R.id.activity_na);
        navigationView = findViewById(R.id.nav);
        menu = findViewById(R.id.iv_menu);

        View headerView = navigationView.getHeaderView(0);
        user_image = headerView.findViewById(R.id.iv_menu_user);
        user_name = headerView.findViewById(R.id.tv_menu_user);
        user_sign = headerView.findViewById(R.id.tv_menu_usersign);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    MyselfUtil rec = new MyselfUtil();
                    MyInfo = rec.httpGet(hostID);
                    handlerPra.sendMessage(handlerPra.obtainMessage(22,MyInfo));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();



        //获取头布局
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击菜单，跳出侧滑菜单
                if (drawerLayout.isDrawerOpen(navigationView)){
                    drawerLayout.closeDrawer(navigationView);
                }else{
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_item1:
                        Intent intent  = new Intent(MainActivity.this,MyCollectActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_item2:
                        Intent intent2  = new Intent(MainActivity.this,MyLikeActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.menu_item3:
                        Intent intent3  = new Intent(MainActivity.this,CommentActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.menu_item4:
                        Intent intent4  = new Intent(MainActivity.this,MyMomentActivity.class);
                        startActivity(intent4);
                        break;



                }
                return true;
            }

        });

    }

    //Fragment
    private void bindViews() {
        txt_topbar = (TextView)findViewById(R.id.txt_topbar);
        txt_channel = (TextView)findViewById(R.id.txt_channel);
        txt_message = (TextView)findViewById(R.id.txt_message);
        txt_setting = (TextView)findViewById(R.id.txt_setting);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);

        txt_channel.setOnClickListener(this);
        txt_message.setOnClickListener(this);
        txt_setting.setOnClickListener(this);
    }

    private void setSelected(){
        txt_channel.setSelected(false);
        txt_message.setSelected(false);
        txt_setting.setSelected(false);
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(channelF != null)fragmentTransaction.hide(channelF);
        if(announceF != null)fragmentTransaction.hide(announceF);
        if(settingF != null)fragmentTransaction.hide(settingF);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()){
            case R.id.txt_channel:
                setSelected();
                txt_topbar.setText(R.string.tab_menu_normal);
                txt_channel.setSelected(true);
                if(channelF == null){
                    channelF = new ChannelFragment();
                    fTransaction.add(R.id.ly_content,channelF);
                }else{
                    fTransaction.show(channelF);
                }break;
            case R.id.txt_message:
                setSelected();
                txt_topbar.setText(R.string.tab_menu_message);
                txt_message.setSelected(true);
                if(announceF == null){
                    announceF = new AnnounceFragment();
                    fTransaction.add(R.id.ly_content,announceF);
                }else{
                    fTransaction.show(announceF);
                }break;
            case R.id.txt_setting:
                setSelected();
                txt_topbar.setText(R.string.tab_menu_setting);
                txt_setting.setSelected(true);
                if(settingF == null){
                    settingF = new FriendFragment();
                    fTransaction.add(R.id.ly_content,settingF);
                }else{
                    fTransaction.show(settingF);
                }break;
        }
        fTransaction.commit();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        Log.v("ImageInfo","image");
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调

                //images = PictureSelector.obtainMultipleResult(data);
                //selectList.addAll(images);
                Log.v("ImageInfo","-------------------------------");

                 //PictureSelector.obtainMultipleResult(data);
                announceF.show(PictureSelector.obtainMultipleResult(data));

                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                //adapter.setList(selectList);
                //adapter.notifyDataSetChanged();
            }
        }
    }
}
