package com.example.retrofit2hiltmvvm.btn02.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.retrofit2hiltmvvm.btn02.net.Test02RequestRepository;
import com.example.retrofit2hiltmvvm.common.bean.ListBean;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class Test02ViewModel extends ViewModel {

    @Inject
    Test02RequestRepository retroRepository;

    @Inject
    public Test02ViewModel() {}

    private MutableLiveData<ListBean> listBean = new MutableLiveData<ListBean>();

    public MutableLiveData<ListBean> getListBean() {
        return listBean;
    }

    public Disposable loadListBean(Integer pageNum) {
        return retroRepository.getListBean(pageNum,listBean);
    }

    public Disposable searchListBean(Map<String,String> keyword) {
        return retroRepository.getSearchListBean(keyword,listBean);
    }
}
