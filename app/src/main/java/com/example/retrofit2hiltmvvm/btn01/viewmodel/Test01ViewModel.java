package com.example.retrofit2hiltmvvm.btn01.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.retrofit2hiltmvvm.btn01.net.Test01RequestRepository;
import com.example.retrofit2hiltmvvm.common.bean.ListBean;
import com.example.retrofit2hiltmvvm.common.net.response.ResponseData;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;

@HiltViewModel
public class Test01ViewModel extends ViewModel {

    @Inject
    Test01RequestRepository retroRepository;

    @Inject
    public Test01ViewModel() {}

    private MutableLiveData<ListBean> listBean = new MutableLiveData<ListBean>();

    public MutableLiveData<ListBean> getListBean() {
        return listBean;
    }

    public Call<ResponseData<ListBean>> loadListBean(Integer pageNum) {
        return retroRepository.getListBean(pageNum,listBean);
    }

    public Call<ResponseData<ListBean>> searchListBean(Map<String,String> keyword) {
        return retroRepository.getSearchListBean(keyword,listBean);
    }
}
