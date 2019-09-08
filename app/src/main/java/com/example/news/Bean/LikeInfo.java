package com.example.news.Bean;

public class LikeInfo {
    private String avatar;
    private String name;

    private String time;
    private String pic;


    public LikeInfo(String avatar, String name, String time, String pic) {
        this.avatar = avatar;
        this.name = name;
        this.time = time;
        this.pic = pic;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setNameetName(String name) {
        this.name = name;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String toString() {
        return "ababab";
    }
}
