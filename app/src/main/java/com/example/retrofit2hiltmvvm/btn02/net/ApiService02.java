package com.example.retrofit2hiltmvvm.btn02.net;

import com.example.retrofit2hiltmvvm.common.bean.ListBean;
import com.example.retrofit2hiltmvvm.common.net.response.ResponseData;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 定义接口
 */
public interface ApiService02 {

    @GET("article/list/{pageNum}/json")
    Observable<ResponseData<ListBean>> getList(@Path("pageNum") Integer pageNum);

    @POST("article/query/0/json")
    @FormUrlEncoded
    Observable<ResponseData<ListBean>> searchList(@FieldMap Map<String,String> keyword);

}
