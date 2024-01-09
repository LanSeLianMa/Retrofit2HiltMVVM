package com.example.retrofit2hiltmvvm.btn02.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.retrofit2hiltmvvm.btn02.viewmodel.Test02ViewModel;
import com.example.retrofit2hiltmvvm.common.adapter.MListAdapter;
import com.example.retrofit2hiltmvvm.common.bean.ListBean;
import com.example.retrofit2hiltmvvm.databinding.AtyTest02Binding;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class Test02Activity extends AppCompatActivity {

    private AtyTest02Binding binding;
    private MListAdapter adapter;
    private Test02ViewModel viewModel;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AtyTest02Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();
        initRv();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 搜索 列表数据
     */
    private void searchData() {
        Random random = new java.util.Random(); // 定义随机类
        int result = random.nextInt(10);

        Map<String, String> map = new HashMap<>();
        map.put("k", String.valueOf(result));

        disposable = viewModel.searchListBean(map); // 请求接口
    }

    /**
     * 初始化加载列表
     */
    private void initList() {
        disposable = viewModel.loadListBean(0); // 请求接口
    }

    /**
     * 初始化加载 列表数据
     */
    private void initData() {
        viewModel = new ViewModelProvider(this).get(Test02ViewModel.class);
        viewModel.getListBean().observe(this, new Observer<ListBean>() {
            @Override
            public void onChanged(ListBean listBean) {
                // Log.d("TAG", "testListBean：" + listBean);
                adapter.setListBean(listBean);
                adapter.notifyDataSetChanged();
            }
        });
        initList();
    }

    /**
     * 初始化 布局
     */
    private void initRv() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new MListAdapter();
        binding.rv.setLayoutManager(manager);
        binding.rv.setAdapter(adapter);

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData();
            }
        });
    }

}