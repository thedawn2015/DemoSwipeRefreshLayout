package com.simon.demoswiperefreshlayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 测试一下，下拉刷新无响应是不是因为直接copy代码的原因。。。
 * 结果。。。当然不是这个原因了。
 * 估计是Fragment的嵌套，加上annotation框架出现的Bug
 * Created by xw on 2016/6/1
 */
public class AnotherActivity extends AppCompatActivity {
    public static String TAG = AnotherActivity.class.getSimpleName();

    @BindView (R.id.another_list_view)
    ListView anotherListView;
    @BindView (R.id.another_swipe_refresh_layout)
    SwipeRefreshLayout anotherSwipeRefreshLayout;

    private MyBaseAdapter myBaseAdapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        ButterKnife.bind(this);

        viewSets();
    }

    private void viewSets() {
        anotherSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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

        myBaseAdapter = new MyBaseAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        anotherListView.setAdapter(myBaseAdapter);

        refreshData();
    }

    private void refreshData() {
        if (!anotherSwipeRefreshLayout.isRefreshing()) {
            anotherSwipeRefreshLayout.setRefreshing(true);
        }
        myBaseAdapter.clear();
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
            anotherSwipeRefreshLayout.setRefreshing(false);
            myBaseAdapter.addItems(list);
        }
    };

}
