package com.example.news.Bean;

public class ContentInfo {
    private String avatar;
    private String Nick;
    private String sign;

    public ContentInfo(String avatar, String nick, String sign) {
        this.avatar = avatar;
        Nick = nick;
        this.sign = sign;
    }

    public ContentInfo() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return Nick;
    }

    public void setNick(String nick) {
        Nick = nick;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String toString(){
        return "ababab";
    }
}
