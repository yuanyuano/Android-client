package com.example.news.Bean;

public class FocusInfo {
    private String avatar;
    private String Nick;
    private String sign;
    private int Focus_bool;

    public FocusInfo(String avatar, String nick, String sign, int focus_bool) {
        this.avatar = avatar;
        Nick = nick;
        this.sign = sign;
        Focus_bool = focus_bool;
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

    public int getFocus_bool() {
        return Focus_bool;
    }

    public void setFocus_bool(int focus_bool) {
        Focus_bool = focus_bool;
    }
}
