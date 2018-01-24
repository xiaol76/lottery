package com.example.chriswang.lottery.meta;

/**
 * Created by chriswang on 18/1/20.
 */

public class WinnerInfo {
    private String name;
    private int avatar;

    public WinnerInfo(int avatar, String name) {
        this.name = name;
        this.avatar = avatar;
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
}
