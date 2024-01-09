package com.example.retrofit2hiltmvvm.btn03.net;

import com.example.retrofit2hiltmvvm.common.bean.ListBean;
import com.example.retrofit2hiltmvvm.common.net.response.ResponseData;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 定义接口
 */
public interface ApiService03 {

    @GET("article/list/{pageNum}/json")
    Single<ResponseData<ListBean>> getList(@Path("pageNum") Integer pageNum);

}
