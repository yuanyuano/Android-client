package com.example.news.Bean;

public class HostInfo {
    private int hostID;
    private String hostNick;
    private String hostAvatar;
    private String password;

    public String getHostAvatar() {
        return hostAvatar;
    }

    public void setHostAvatar(String hostAvatar) {
        this.hostAvatar = hostAvatar;
    }

    public HostInfo(int hostID, String hostNick, String hostAvadatar, String password) {
        this.hostID = hostID;
        this.hostNick = hostNick;
        this.hostAvatar = hostAvadatar;
        this.password = password;
    }

    public int getHostID() {
        return hostID;
    }

    public void setHostID(int hostID) {
        this.hostID = hostID;
    }

    public String getHostNick() {
        return hostNick;
    }

    public void setHostNick(String hostNick) {
        this.hostNick = hostNick;
    }

    public String getHostAvadatar() {
        return hostAvatar;
    }

    public void setHostAvadatar(String hostAvadatar) {
        this.hostAvatar = hostAvadatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "hostInfo{" +
                "hostID=" + hostID +
                ", hostNick='" + hostNick + '\'' +
                ", hostAvatar='" + hostAvatar + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
