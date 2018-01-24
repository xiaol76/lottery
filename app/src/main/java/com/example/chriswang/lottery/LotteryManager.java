package com.example.chriswang.lottery;

import com.example.chriswang.lottery.meta.WinnerInfo;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by chriswang on 18/1/20.
 */

public class LotteryManager {
    private ArrayList<WinnerInfo> mParticipantList = new ArrayList<>();
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
        mParticipantList.add(new WinnerInfo(R.drawable.img268, "卞萍"));
        mParticipantList.add(new WinnerInfo(R.drawable.img269, "小七"));
        mParticipantList.add(new WinnerInfo(R.drawable.img270, "加喜"));
        mParticipantList.add(new WinnerInfo(R.drawable.img271, "怡宏"));
        mParticipantList.add(new WinnerInfo(R.drawable.img272, "王萍"));
        mParticipantList.add(new WinnerInfo(R.drawable.img273, "安竖"));
        mParticipantList.add(new WinnerInfo(R.drawable.img274, "周洵"));
        mParticipantList.add(new WinnerInfo(R.drawable.img275, "静静"));
        mParticipantList.add(new WinnerInfo(R.drawable.img276, "如轩"));
        mParticipantList.add(new WinnerInfo(R.drawable.img277, "嘉欣"));
        mParticipantList.add(new WinnerInfo(R.drawable.img278, "刘强"));
        mParticipantList.add(new WinnerInfo(R.drawable.img279, "慧丹"));
        mParticipantList.add(new WinnerInfo(R.drawable.img280, "房钦"));
        mParticipantList.add(new WinnerInfo(R.drawable.img281, "小乔"));
        mParticipantList.add(new WinnerInfo(R.drawable.img282, "程行健"));
    }

    public WinnerInfo getWinner(int level) {
        ArrayList<WinnerInfo> firstWinnerList = new ArrayList<>();
        for (WinnerInfo winner : mParticipantList) {
            if (isInBlackList(winner) && (level == 1 || level == 2 || level == 3)) {
                continue;
            }
            firstWinnerList.add(winner);
        }
        WinnerInfo firstWinner = firstWinnerList.get(new Random().nextInt(firstWinnerList.size()));
        mParticipantList.remove(firstWinner);
        return firstWinner;
    }

    public ArrayList<WinnerInfo> getParticipantList() {
        return mParticipantList;
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
