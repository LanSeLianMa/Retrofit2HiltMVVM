package com.example.retrofit2hiltmvvm.btn01.net;

import com.example.retrofit2hiltmvvm.common.bean.ListBean;
import com.example.retrofit2hiltmvvm.common.net.response.ResponseData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 定义接口
 */
public interface ApiService01 {

    @GET("article/list/{pageNum}/json")
    Call<ResponseData<ListBean>> getList(@Path("pageNum") Integer pageNum);

    /**
     * 替换不同的 BaseUrl
     */
    // @Headers("BaseUrlName:baidu")
    // @GET("article/list/{pageNum}/json")
    // Call<ResponseData<ListBean>> getList02(@Path("pageNum") Integer pageNum);

    @POST("article/query/0/json")
    @FormUrlEncoded
    Call<ResponseData<ListBean>> searchList(@FieldMap Map<String,String> keyword);

}
