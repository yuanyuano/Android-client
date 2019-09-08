package com.example.news.Bean;

public class foucs_friend {
    private int hostID;
    private int friendID;

    public foucs_friend(int hostID, int friendID) {
        this.hostID = hostID;
        this.friendID = friendID;
    }

    public int getHostID() {
        return hostID;
    }

    public void setHostID(int hostID) {
        this.hostID = hostID;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }
}
