package com.example.retrofit2hiltmvvm.btn04.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import com.example.retrofit2hiltmvvm.btn04.net.Test04RequestRepository;
import com.example.retrofit2hiltmvvm.common.bean.ListBean;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Test04ViewModel extends ViewModel {

    @Inject
    Test04RequestRepository retroRepository;

    @Inject
    public Test04ViewModel() {}

    public boolean isReset = false;

    private MutableLiveData<ListBean> listBean = new MutableLiveData<ListBean>();

    public MutableLiveData<ListBean> getListBean() {
        return listBean;
    }

    public LiveData<PagingData<ListBean.DatasBean>> listenableFutureLoadList() {
        return retroRepository.listenableFutureLoadList(this);
    }

}
