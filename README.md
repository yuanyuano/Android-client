# 项目简介

## 项目框架

<img src="https://s2.ax1x.com/2019/09/08/nGeBh4.png" width="250"/> <img src="https://s2.ax1x.com/2019/09/08/nGeBh4.png" width="250"/> <img src="https://s2.ax1x.com/2019/09/08/nGeBh4.png" width="250"/>

## 网络传输
我们采用OKHTTP3的网络方式与服务器进行数据交互，将数据打包成Jason格式进行传输。我这里只给客户端的代码。

# 主要功能及代码分析
## 注册和登录
__初始界面：用户选择登录or注册__

<img src="https://s2.ax1x.com/2019/09/08/nGeNn0.png" width="300"/> 

__注册界面&登录界面__

<img src="https://s2.ax1x.com/2019/09/08/nGe6j1.png" width="300"/> <img src="https://s2.ax1x.com/2019/09/08/nGeRHK.png" width="300"/>

_登录和注册功能的实现主要使用**OKHTTP3**方式与服务器进行交互_，两者的技术实现几乎完全一样。

__登录__——get用户信息，向服务器发送登录请求

```javascript
String jsonstr = new Gson().toJson(hostInfo); //数据打包为Jason格式
RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonstr);
OkHttpClient client = new OkHttpClient();
//向服务器发送请求
Request request = new Request.Builder().url("http://192.168.43.121:8080/login").post(body).build();
//这里的http://192.168.43.121:8080就是我们服务器的url，login是为登录创建的存储文件夹

client.newCall(request).enqueue(new Callback() {  //收到服务器回复
    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String back = response.body().string();

        if(back.equals("success")){  //若收到服务器的回复为success，则成功。（这个success是你与服务器约定好的一个字符串）
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
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
```

__注册__——get用户信息，向服务器发送注册请求
```javascript
String jsonstr = new Gson().toJson(hostInfo);     //数据打包成Jason格式
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
```

_提醒：_ 注册的上传头像功能我用了一个第三方工具，我自己写的老是遇到各种报错问题，建议用第三方实现，第三方工具在后面会给出链接。
## 整体布局
登录成功以后进入主界面——动态页面，侧滑栏为 __个人信息__。

<img src="https://s2.ax1x.com/2019/09/08/nGegnx.png" width="300"/> <img src="https://s2.ax1x.com/2019/09/08/nGe0NF.png" width="300"/> 

列表的布局采用ListView、数据源和Adapter配合实现。

```javascript
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
```

## 搜索和关注好友

<img src="https://s2.ax1x.com/2019/09/08/nGeycR.png" width="300"/> <img src="https://s2.ax1x.com/2019/09/08/nGe2B6.png" width="300"/>

__查询好友id：__
_将id提交给服务器查询，根据收到的其回复来判断是否存在该用户_

```javascript
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
```
__关注好友：__

_将数据提交给服务器，服务器将关注信息保存到 **MySQL** 数据库中_

```javascript
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
```

## 浏览动态（点赞评论收藏） 

<img src="https://s2.ax1x.com/2019/09/08/nGefAO.png" height="550"/> <img src="https://s2.ax1x.com/2019/09/08/nGe44e.png" height="550"/>

点赞、评论和收藏全部放在一个 __Fragment__ 中，点赞和收藏可以直接向服务器发送请求，但是评论需要一个单独的Activity来get用户输入的具体评论信息，在那个Activity里面再进行数据上传，Fragment里面只调用这个Activity即可。

```javascript
//在Fragment中实现点赞、评论和收藏功能

//收藏
adapter.setOnItemCollectListener(new ViewAdapter.onItemCollectListener() {
    @Override
    public void onCollectClick(final int i) {
        PraiseOrCollectMsg msg = new PraiseOrCollectMsg();
        msg.setDynamicID(list_item.get(i).getDynamicID());
        msg.setHostID(1);
        String jsonstr = new Gson().toJson(msg);
        //向服务器发送请求
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
                Message message = new Message();
                message.what = 0;
                handlerPra.sendMessage(message);
            }
        });
    }
});

//评论
adapter.setOnItemCommentClickListener(new ViewAdapter.onItemCommentListener() {
    @Override
    public void onCommentClick(int i) {     
        System.out.println("");
        comment(i);     //调用评论功能的Activity
    }
});

//点赞
adapter.setOnItemPraiseClickListener(new ViewAdapter.onItemPraiseListener() {
    @Override
    public void onPraiseClick(final int i) {
        PraiseOrCollectMsg msg = new PraiseOrCollectMsg();
        msg.setDynamicID(list_item.get(i).getDynamicID());
        msg.setHostID(1);
        String jsonstr = new Gson().toJson(msg);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonstr);
        OkHttpClient client = new OkHttpClient();
        //向服务器发送请求
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
                Message message = new Message();
                message.what = 0;
                handlerPra.sendMessage(message);
            }
        });
    }
});

```

## 发布动态

<img src="https://s2.ax1x.com/2019/09/08/nGeUBV.png" width="300"/>

把发布动态功能放在Fragment里面，发布功能实现还是利用OKHTTP3网络传输。图片上传仍使用第三方工具。
我这里放一下  _第三方工具包_：[PictureSelector](https://blog.csdn.net/yechaoa/article/details/79291552)

```javascript
private void showAlbum() {
    //参数很多，根据需要添加
    PictureSelector.create(getActivity())
	        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
	        .maxSelectNum(maxSelectNum)// 最大图片选择数量
	        .minSelectNum(1)// 最小选择数量
	        .imageSpanCount(4)// 每行显示个数
	        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
	        .previewImage(true)// 是否可预览图片
	        .isCamera(true)// 是否显示拍照按钮
	        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
	        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
	        .enableCrop(true)// 是否裁剪
	        .compress(true)// 是否压缩
	        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
	        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
	        .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
	        //.selectionMedia(selectList)// 是否传入已选图片
	        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
	        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
	        //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
	        //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
	        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
	        .rotateEnabled(false) // 裁剪是否可旋转图片
	        .scaleEnabled(true)// 裁剪是否可放大缩小图片
	        //.recordVideoSecond()//录制视频秒数 默认60s
	        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
}
```

## 个人信息

<img src="https://s2.ax1x.com/2019/09/08/nGegnx.png" width="300"/>

对于收到的点赞/评论/我的收藏，均使用 _Handler机制_ 开启一个新的线程，搭配适配器进行页面显示。

__我收到的点赞：__

<img src="https://s2.ax1x.com/2019/09/08/nGVraF.png" width="300"/>

```javascript
Handler handler = new Handler(){
    @Override
    public void handleMessage(@NonNull Message msg) {
        lv.setAdapter(new LikeAdapter(MyLikeActivity.this,(List<LikeInfo>) msg.obj));
    }
};
```

__我收到的评论：__

<img src="https://s2.ax1x.com/2019/09/08/nGeI9H.png" width="300"/>

```javascript
Handler handlerPra = new Handler(){
    @Override
    public void handleMessage(Message msg){
        if(msg.what == 0){
            adapter.notifyDataSetChanged();
        }
    }
};
```

__我的收藏：__

<img src="https://s2.ax1x.com/2019/09/08/nGehND.png" width="300"/>

```javascript
Handler handlerPra = new Handler(){
    @Override
    public void handleMessage(Message msg){
        if(msg.what == 0){
            adapter.notifyDataSetChanged();
        }
    }
};
```

# 总结
整个项目大致的主要功能就是如此啦，技术上难度不算太大，每个功能的实现都是在重复地使用OKHTTP3与服务器进行数据交互，必要时使用Fragment代替Activity，列表显示就结合Adapter来实现，我遇到最大的问题倒是在图片上传那里，所以我强烈建议使用第三方，拍照、选择图片、图片裁剪、缩放等等，功能很全了。这次的安卓开发项目完成度比较高，功能低仿微信，在真机上运行没有太大的问题，算是一次小小的突破。不过中间有些bug，是在整合的时候出现的，由于时间有限我们没有太追究这些细枝末节，仅作学习参考。欢迎大家和我一起交流！

# 协议

本项目遵从

