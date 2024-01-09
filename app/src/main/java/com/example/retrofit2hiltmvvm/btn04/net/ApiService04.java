package com.example.retrofit2hiltmvvm.btn04.net;

import com.example.retrofit2hiltmvvm.common.bean.ListBean;
import com.example.retrofit2hiltmvvm.common.net.response.ResponseData;
import com.google.common.util.concurrent.ListenableFuture;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 定义接口
 */
public interface ApiService04 {

    @GET("article/list/{pageNum}/json")
    ListenableFuture<ResponseData<ListBean>> getList(@Path("pageNum") Integer pageNum);

}
