package com.example.retrofit2hiltmvvm.btn02.net;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.retrofit2hiltmvvm.common.bean.ListBean;
import com.example.retrofit2hiltmvvm.common.net.exception.ApiException;
import com.example.retrofit2hiltmvvm.common.net.exception.ErrorConsumer;
import com.example.retrofit2hiltmvvm.common.net.response.ResponseTransformer;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 请求接口数据
 */
public class Test02RequestRepository {

    @Inject
    ApiService02 apiService02;

    @Inject
    public Test02RequestRepository() {
    }

    @SuppressLint("CheckResult")
    public Disposable getSearchListBean(Map<String, String> keyword, MutableLiveData<ListBean> listBean) {

        return apiService02.searchList(keyword)
                .subscribeOn(Schedulers.io()) // 调度线程，表示在子线程执行
                .observeOn(AndroidSchedulers.mainThread()) // 执行完成后，切换回主线程
                .compose(ResponseTransformer.obtain())
                .subscribe(new Consumer<ListBean>() {
                    @Override
                    public void accept(ListBean data) throws Throwable {
                        // 响应回调
                        listBean.postValue(data);
                    }
                }, new ErrorConsumer() {
                    @Override
                    protected void error(ApiException e) {
                        Log.e("TAG", "err：" + e.getCode() + " --- msg：" + e.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Throwable {
                        // 请求完成回调
                    }
                });

    }

    @SuppressLint("CheckResult")
    public Disposable getListBean(Integer pageNum, MutableLiveData<ListBean> listBean) {

        return apiService02.getList(pageNum)
                .subscribeOn(Schedulers.io()) // 调度线程，表示在子线程执行
                .observeOn(AndroidSchedulers.mainThread()) // 执行完成后，切换回主线程
                .compose(ResponseTransformer.obtain())
                .subscribe(new Consumer<ListBean>() {
                    @Override
                    public void accept(ListBean data) throws Throwable {
                        // 响应回调
                        listBean.postValue(data);
                    }
                }, new ErrorConsumer() {
                    @Override
                    protected void error(ApiException e) {
                        Log.e("TAG", "err：" + e.getCode() + " --- msg：" + e.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Throwable {
                        // 请求完成回调
                    }
                });

    }

}
