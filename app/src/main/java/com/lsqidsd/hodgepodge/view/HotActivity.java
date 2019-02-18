package com.lsqidsd.hodgepodge.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.ActivityHotAdapter;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.databinding.ActivityHotBinding;
import com.lsqidsd.hodgepodge.viewmodel.HotViewModule;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class HotActivity extends BaseActivity implements InterfaceListenter.HotNewsDataListener {
    private HotViewModule hotViewModule;
    private ActivityHotBinding activityHotBinding;
    private InterfaceListenter.HotNewsDataListener hotNewsDataListener;
    private ActivityHotAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.activity_hot;
    }

    @Override
    public void initView() {
        hotNewsDataListener = this;
        hotViewModule = new HotViewModule(this);
        activityHotBinding = getBinding(activityHotBinding);
        activityHotBinding.lv.tv.setText("热点精选");
        activityHotBinding.lv.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        activityHotBinding.setHotview(hotViewModule);
        //activityHotBinding.refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        activityHotBinding.refreshLayout.setOnRefreshListener(a -> refresh());
        activityHotBinding.refreshLayout.autoRefresh();
    }

    private void refresh() {
        List<NewsHot.DataBean> dataBeans = new ArrayList<>();
        HttpModel.getActivityHotNews(0, hotNewsDataListener, dataBeans, activityHotBinding.refreshLayout);
    }

    @Override
    public void hotDataChange(List<NewsHot.DataBean> dataBeans) {
        adapter = new ActivityHotAdapter(this, dataBeans, activityHotBinding.refreshLayout);
        activityHotBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        activityHotBinding.rv.setAdapter(adapter);
    }
}
