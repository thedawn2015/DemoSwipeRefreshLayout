package com.simon.demoswiperefreshlayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //    @BindView (R.id.list_view)
    //    ListView listView;

    public static String TAG = MainActivity.class.getSimpleName();
    @BindView (R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView (R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView (R.id.btn_another_activity)
    Button btnAnotherActivity;

    private MyBaseAdapter adapter;
    private MyRecyclerViewAdapter recyclerViewAdapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);
        ButterKnife.bind(this);

        viewSets();
    }

    private void viewSets() {
        //Modified By xw 2016/6/1  Explain：下拉回调
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh: ");
                refreshData();
            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("name+" + i);
        }

        recyclerViewAdapter = new MyRecyclerViewAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(recyclerViewAdapter);

        /*adapter = new MyBaseAdapter(this);
        adapter.addItems(list);
        listView.setAdapter(adapter);*/
        refreshData();
    }

    private void refreshData() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
        recyclerViewAdapter.clear();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            swipeRefreshLayout.setRefreshing(false);
            recyclerViewAdapter.addItems(list);
        }
    };

    @OnClick (R.id.btn_another_activity)
    public void onClick() {
        Intent intent = new Intent(this, AnotherActivity.class);
        startActivity(intent);
    }
}
