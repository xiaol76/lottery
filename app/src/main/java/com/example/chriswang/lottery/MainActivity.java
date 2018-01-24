package com.example.chriswang.lottery;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chriswang.lottery.meta.WinnerInfo;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ParticipantAdapter mAdapter;
    private TextView mStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transparentStatusBar(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.participantList);
        mStart = (TextView) findViewById(R.id.start);
        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        mAdapter = new ParticipantAdapter();
        mRecyclerView.setAdapter(mAdapter);
        final PopupMenu popupMenu = new PopupMenu(MainActivity.this, mStart);
        popupMenu.getMenu().add(R.id.menuGroup, R.id.menu1, 1, R.string.first);
        popupMenu.getMenu().add(R.id.menuGroup, R.id.menu2, 2, R.string.second);
        popupMenu.getMenu().add(R.id.menuGroup, R.id.menu3, 3, R.string.third);
        popupMenu.getMenu().add(R.id.menuGroup, R.id.menu4, 4, R.string.forth);
        popupMenu.getMenu().add(R.id.menuGroup, R.id.menu5, 5, R.string.fifth);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                popupMenu.getMenu().removeItem(item.getItemId());
                LuckyPrizeActivity.launch(MainActivity.this, item.getTitle().toString());
                return true;
            }
        });
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    private class ParticipantAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WinnerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_winner, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            WinnerInfo winner = LotteryManager.getInstance().getWinnerList().get(position);
            ((WinnerHolder) holder).render(winner);
        }

        @Override
        public int getItemCount() {
            return LotteryManager.getInstance().getWinnerList().size();
        }
    }

    private class WinnerHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView nickname;

        public WinnerHolder(View itemView) {
            super(itemView);
            int width = (getResources().getDisplayMetrics().widthPixels - Utils.dip2px(5) * 4) / 4;
            int height = width;
            avatar = itemView.findViewById(R.id.avatar);
            avatar.getLayoutParams().width = width;
            avatar.getLayoutParams().height = height;
            nickname = itemView.findViewById(R.id.nickname);
        }

        public void render(WinnerInfo winnerInfo) {
//            Glide.with(MainActivity.this).load(winnerInfo.getAvatar()).apply(RequestOptions.circleCropTransform()).into(avatar);
            Glide.with(MainActivity.this).load(winnerInfo.getAvatar()).into(avatar);
            nickname.setText(winnerInfo.getName());
        }
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
}
