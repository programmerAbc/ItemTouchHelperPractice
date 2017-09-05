package com.practice.itemtouchhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainRv)
    RecyclerView mainRv;
    MyAdapter myAdapter;
    boolean deleteZoneShown = false;
    @BindView(R.id.deleteZone)
    LinearLayout deleteZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myAdapter = new MyAdapter();
        mainRv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        myAdapter.setupWithRecyclerView(mainRv);
        EventBus.getDefault().register(this);
    }

    public void showDeleteZone() {
        deleteZone.animate().translationY(-getResources().getDimension(R.dimen.delete_zone_height)).start();
    }

    public void hideDeleteZone() {
        deleteZone.animate().translationY(0).start();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventDragEnd(EventDragEnd event) {
        hideDeleteZone();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventDragStart(EventDragStart event) {
        showDeleteZone();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick(R.id.actionBtn)
    public void onViewClicked() {
        if (deleteZoneShown) {
            hideDeleteZone();
        } else {
            showDeleteZone();
        }
        deleteZoneShown = !deleteZoneShown;
    }
}
