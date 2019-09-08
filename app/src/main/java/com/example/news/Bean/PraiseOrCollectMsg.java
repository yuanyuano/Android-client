package com.example.news.Bean;

import java.io.Serializable;

public class PraiseOrCollectMsg implements Serializable {
    public int dynamicID;
    public int hostID;

    public int getDynamicID() {
        return dynamicID;
    }

    public void setDynamicID(int dynamicID) {
        this.dynamicID = dynamicID;
    }

    public int getHostID() {
        return hostID;
    }

    public void setHostID(int hostID) {
        this.hostID = hostID;
    }
}
