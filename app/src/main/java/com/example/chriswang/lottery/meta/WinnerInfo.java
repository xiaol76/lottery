package com.example.chriswang.lottery.meta;

/**
 * Created by chriswang on 18/1/20.
 */

public class WinnerInfo {
    public static final int HEADER = 1;
    public static final int ITEM = 2;
    private String name;
    private int avatar;
    private int type;
    private String title;

    public WinnerInfo(int avatar, String name) {
        this.name = name;
        this.avatar = avatar;
        this.type = ITEM;
    }

    public WinnerInfo(String title) {
        this.type = HEADER;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
