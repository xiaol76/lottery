package com.example.chriswang.lottery;

import com.example.chriswang.lottery.meta.WinnerInfo;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by chriswang on 18/1/20.
 */

public class LotteryManager {
    private ArrayList<WinnerInfo> mWinnerList = new ArrayList<>();

    private static LotteryManager sInstance;

    private LotteryManager() {
        init();
    }

    public static synchronized LotteryManager getInstance() {
        if (sInstance == null) {
            sInstance = new LotteryManager();
        }
        return sInstance;
    }

    private void init() {
        mWinnerList.add(new WinnerInfo(R.drawable.img268, "卞萍"));
        mWinnerList.add(new WinnerInfo(R.drawable.img269, "小七"));
        mWinnerList.add(new WinnerInfo(R.drawable.img270, "加喜"));
        mWinnerList.add(new WinnerInfo(R.drawable.img271, "怡宏"));
        mWinnerList.add(new WinnerInfo(R.drawable.img272, "王萍"));
        mWinnerList.add(new WinnerInfo(R.drawable.img273, "安竖"));
        mWinnerList.add(new WinnerInfo(R.drawable.img274, "周洵"));
        mWinnerList.add(new WinnerInfo(R.drawable.img275, "静静"));
        mWinnerList.add(new WinnerInfo(R.drawable.img276, "如轩"));
        mWinnerList.add(new WinnerInfo(R.drawable.img277, "嘉欣"));
        mWinnerList.add(new WinnerInfo(R.drawable.img278, "刘强"));
        mWinnerList.add(new WinnerInfo(R.drawable.img279, "慧丹"));
        mWinnerList.add(new WinnerInfo(R.drawable.img280, "房钦"));
        mWinnerList.add(new WinnerInfo(R.drawable.img281, "小乔"));
        mWinnerList.add(new WinnerInfo(R.drawable.img282, "程行健"));
    }

    public WinnerInfo getWinner(int level) {
        ArrayList<WinnerInfo> firstWinnerList = new ArrayList<>();
        for (WinnerInfo winner : mWinnerList) {
            if (isInBlackList(winner) && (level == 1 || level == 2 || level == 3)) {
                continue;
            }
            firstWinnerList.add(winner);
        }
        WinnerInfo firstWinner = firstWinnerList.get(new Random().nextInt(firstWinnerList.size()));
        mWinnerList.remove(firstWinner);
        return firstWinner;
    }

    public ArrayList<WinnerInfo> getWinnerList() {
        return mWinnerList;
    }

    private boolean isInBlackList(WinnerInfo winner) {
        if (winner.getAvatar() == R.drawable.img269 || winner.getAvatar() == R.drawable.img275 || winner.getAvatar() == R.drawable.img277 || winner.getAvatar() == R.drawable.img282) {
            return true;
        }
        return false;
    }
}
