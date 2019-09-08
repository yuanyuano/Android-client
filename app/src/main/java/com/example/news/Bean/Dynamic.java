package com.example.news.Bean;

import android.net.Uri;
import android.widget.ImageView;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class Dynamic implements Serializable {

    private int dynamicID;
    private String hostAvatar;
    private String hostNick;
    private String createTime;
    private String mom_textField;
    private String mom_image;
    private int hasPraised;
    private int isCollected;
    private int isPraised;
    //private int commentID;

    public Dynamic(int dynamicID, String hostAvatar, String hostNick, String createTime, String mom_textField, String mom_image, int hasPraised, int isCollected, int isPraised) {
        this.dynamicID = dynamicID;
        this.hostAvatar = hostAvatar;
        this.hostNick = hostNick;
        this.createTime = createTime;
        this.mom_textField = mom_textField;
        this.mom_image = mom_image;
        this.hasPraised = hasPraised;
        this.isCollected = isCollected;
        this.isPraised = isPraised;
    }

    public int getDynamicID() {
        return dynamicID;
    }

    public void setDynamicID(int dynamicID) {
        this.dynamicID = dynamicID;
    }

    public String getHostAvatar() {
        return hostAvatar;
    }

    public void setHostAvatar(String hostAvatar) {
        this.hostAvatar = hostAvatar;
    }

    public String getHostNick() {
        return hostNick;
    }

    public void setHostNick(String hostNick) {
        this.hostNick = hostNick;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMom_textField() {
        return mom_textField;
    }

    public void setMom_textField(String mom_textField) {
        this.mom_textField = mom_textField;
    }

    public String getMom_image() {
        return mom_image;
    }

    public void setMom_image(String mom_image) {
        this.mom_image = mom_image;
    }

    public int getHasPraised() {
        return hasPraised;
    }

    public void setHasPraised(int hasPraised) {
        this.hasPraised = hasPraised;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(int isCollected) {
        this.isCollected = isCollected;
    }

    public int getIsPraised() {
        return isPraised;
    }

    public void setIsPraised(int isPraised) {
        this.isPraised = isPraised;
    }

    public String toString() {
        return "dy[" +
                "avatar=" + hostAvatar +"\n"+
                ", name=" + hostNick +
                ", creatTime='" + createTime.toString() +"\n"+
                ", image='" + mom_image +"\n"+
                ", hasPraised=" + hasPraised +
                ",isCollected=" + isCollected +
                ",isPraised=" + isPraised +
                ",dynamicID=" + dynamicID +
                ",content=" + mom_textField+
                ']';
    }
}
