package com.example.retrofit2hiltmvvm.btn04.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.retrofit2hiltmvvm.btn04.viewmodel.Test04ViewModel;
import com.example.retrofit2hiltmvvm.common.adapter.MPagingDataAdapter;
import com.example.retrofit2hiltmvvm.databinding.AtyTest03Binding;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Test04Activity extends AppCompatActivity {

    private AtyTest03Binding binding;
    private Test04ViewModel viewModel;
    private MPagingDataAdapter pagingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = AtyTest03Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(Test04ViewModel.class);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rv.setLayoutManager(manager);

        pagingAdapter = new MPagingDataAdapter();
        binding.rv.setAdapter(pagingAdapter);

        // Guava + LiveData
        viewModel.listenableFutureLoadList().observe(this, pagingData ->
                pagingAdapter.submitData(getLifecycle(), pagingData));

        // viewModel.getListBean().observe(this, new Observer<ListBean>() {
        //    @Override
        //    public void onChanged(ListBean listBean) {
        //        // 完整的请求数据
        //        Log.d("TAG","listBean："+listBean.getPageCount());
        //    }
        // });

        pagingAdapter.addLoadStateListener(it -> {

            // 下拉刷新
            if (it.getRefresh() instanceof LoadState.NotLoading) { // 刷新结束

                if (it.getRefresh().getEndOfPaginationReached()) { // 没有更多数据了
                    binding.refreshLayout.finishRefreshWithNoMoreData();
                } else {
                    binding.refreshLayout.finishRefresh();
                }

            } else if (it.getRefresh() instanceof LoadState.Loading) {

            } else if (it.getRefresh() instanceof LoadState.Error) {
                LoadState.Error state = (LoadState.Error) it.getRefresh();
                Log.d("TAG", "err：" + state.getError().getMessage());
                binding.refreshLayout.finishRefresh(false);
            }

            // 上拉加载更多
            if (it.getAppend() instanceof LoadState.NotLoading) { // 刷新结束

                if (it.getAppend().getEndOfPaginationReached()) { // 没有更多数据了
                    binding.refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    binding.refreshLayout.finishLoadMore();
                }

            } else if (it.getAppend() instanceof LoadState.Loading) {

            } else if (it.getAppend() instanceof LoadState.Error) {
                LoadState.Error state = (LoadState.Error) it.getAppend();
                Log.d("TAG", "err：" + state.getError().getMessage());
                binding.refreshLayout.finishLoadMore(false);
            }

            return null;
        });

        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pagingAdapter.refresh();
            }
        });

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.isReset = true;
                pagingAdapter.refresh();
                binding.rv.scrollToPosition(0);
            }
        });

    }

}