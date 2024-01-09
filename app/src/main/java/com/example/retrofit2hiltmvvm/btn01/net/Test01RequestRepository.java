package com.example.retrofit2hiltmvvm.btn01.net;

import androidx.lifecycle.MutableLiveData;

import com.example.retrofit2hiltmvvm.common.bean.ListBean;
import com.example.retrofit2hiltmvvm.common.net.response.ResponseData;

import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 请求接口数据
 */
public class Test01RequestRepository {

    @Inject
    ApiService01 apiService01; // 注意：自动实例化 不能使用 private 修饰符

    @Inject
    public Test01RequestRepository() {}

    public Call<ResponseData<ListBean>> getSearchListBean(Map<String, String> keyword, MutableLiveData<ListBean> listBean) {
        Call<ResponseData<ListBean>> responseCall = apiService01.searchList(keyword);
        responseCall.enqueue(new Callback<ResponseData<ListBean>>() {
            @Override
            public void onResponse(Call<ResponseData<ListBean>> call, Response<ResponseData<ListBean>> response) {
                // Log.d("TAG", "onResponse：" + response.body().getData().getDatas().size());
                listBean.postValue(response.body().getData()); // 赋值给实体
            }

            @Override
            public void onFailure(Call<ResponseData<ListBean>> call, Throwable t) {
                // Log.d("TAG", "onFailure：" + t);
            }
        });
        return responseCall;
    }

    public Call<ResponseData<ListBean>> getListBean(Integer pageNum, MutableLiveData<ListBean> listBean) {
        Call<ResponseData<ListBean>> responseCall = apiService01.getList(pageNum);
        responseCall.enqueue(new Callback<ResponseData<ListBean>>() {
            @Override
            public void onResponse(Call<ResponseData<ListBean>> call, Response<ResponseData<ListBean>> response) {
                // Log.d("TAG", "onResponse：" + response.body().getData().getDatas().size());
                listBean.postValue(response.body().getData()); // 赋值给实体
            }

            @Override
            public void onFailure(Call<ResponseData<ListBean>> call, Throwable t) {
                // Log.d("TAG", "onFailure：" + t);
            }
        });
        return responseCall;
    }

}
