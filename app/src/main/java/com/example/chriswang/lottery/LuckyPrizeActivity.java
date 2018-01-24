package com.example.chriswang.lottery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chriswang.lottery.meta.WinnerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by chriswang on 18/1/21.
 */

public class LuckyPrizeActivity extends AppCompatActivity {
    private static final String PRIZE_LEVEL = "prize_level";
    private ImageView mLuckyImage;
    private TextView mStart;
    private TextView mLevelText;
    private RecyclerView mRecyclerView;
    private ParticipantAdapter mAdapter;
    private ArrayList<WinnerInfo> mList = new ArrayList<>();
    private Handler mHandler = new Handler();
    private boolean isStarted;
    private boolean isReturned;
    private int mLevel;
    private int mCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luckyprize);
        transparentStatusBar(true);
        mLevel = getLevel(getIntent().getStringExtra(PRIZE_LEVEL));
        LotteryManager.getInstance().getWinnerList().add(new WinnerInfo(getIntent().getStringExtra(PRIZE_LEVEL)));
        mLuckyImage = (ImageView) findViewById(R.id.luckyWinenr);
        mLevelText = (TextView) findViewById(R.id.level);
        mLevelText.setText(getIntent().getStringExtra(PRIZE_LEVEL));
        mStart = (TextView) findViewById(R.id.start);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReturned) {
                    LuckyPrizeActivity.this.finish();
                    return;
                }
                if (isStarted) {
                    isStarted = false;
                    mHandler.removeCallbacksAndMessages(null);
                    WinnerInfo winnerInfo = LotteryManager.getInstance().getWinner(mLevel);
                    LotteryManager.getInstance().getWinnerList().add(winnerInfo);
                    mCount++;
                    mList.add(winnerInfo);
                    mAdapter.notifyDataSetChanged();
                    Glide.with(LuckyPrizeActivity.this).load(winnerInfo.getAvatar()).into(mLuckyImage);
                    if (mLevel == mCount) {
                        mStart.setText(R.string.other);
                        isReturned = true;
                    } else {
                        mStart.setText(R.string.start);
                    }
                } else {
                    if (mLevel == 0) {
                        Toast.makeText(LuckyPrizeActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    }
                    isStarted = true;
                    showRandomWinner();
                    mStart.setText(R.string.stop);
                }
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.winnerList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(LuckyPrizeActivity.this, 5));
        mAdapter = new ParticipantAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private int getLevel(String level) {
        if (level.equals(getString(R.string.first))) {
            mLevel = 1;
        } else if (level.equals(getString(R.string.second))) {
            mLevel = 2;
        } else if (level.equals(getString(R.string.third))) {
            mLevel = 3;
        } else if (level.equals(getString(R.string.forth))) {
            mLevel = 4;
        } else if (level.equals(getString(R.string.fifth))) {
            mLevel = 5;
        }
        return mLevel;
    }

    private void showRandomWinner() {
        final List<WinnerInfo> winnerInfoList = LotteryManager.getInstance().getParticipantList();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                WinnerInfo winner = winnerInfoList.get(new Random().nextInt(winnerInfoList.size()));
                Glide.with(LuckyPrizeActivity.this).load(winner.getAvatar()).into(mLuckyImage);
                mHandler.postDelayed(this, 100);
            }
        }, 100);
    }

    public void transparentStatusBar(boolean enable) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            if (enable) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (enable) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
        }
    }

    private class ParticipantAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WinnerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_winner, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position >= mList.size()) {
                return;
            }
            WinnerInfo winner = mList.get(position);
            ((WinnerHolder) holder).render(winner);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private class WinnerHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView nickname;

        public WinnerHolder(View itemView) {
            super(itemView);
            int width = (getResources().getDisplayMetrics().widthPixels - Utils.dip2px(5) * 4) / 5;
            int height = width;
            avatar = itemView.findViewById(R.id.avatar);
            avatar.getLayoutParams().width = width;
            avatar.getLayoutParams().height = height;
            nickname = itemView.findViewById(R.id.nickname);
        }

        public void render(WinnerInfo winnerInfo) {
            Glide.with(LuckyPrizeActivity.this).load(winnerInfo.getAvatar()).apply(RequestOptions.circleCropTransform()).into(avatar);
            nickname.setText(winnerInfo.getName());
        }
    }

    public static void launch(Context context, String level) {
        Intent intent = new Intent(context, LuckyPrizeActivity.class);
        intent.putExtra(PRIZE_LEVEL, level);
        context.startActivity(intent);
    }
}
