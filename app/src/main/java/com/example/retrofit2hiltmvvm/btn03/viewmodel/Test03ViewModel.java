package com.example.retrofit2hiltmvvm.btn03.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import com.example.retrofit2hiltmvvm.btn03.net.Test03RequestRepository;
import com.example.retrofit2hiltmvvm.common.bean.ListBean;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;

@HiltViewModel
public class Test03ViewModel extends ViewModel {

    @Inject
    Test03RequestRepository retroRepository;

    @Inject
    public Test03ViewModel() {}

    public boolean isReset = false;

    private MutableLiveData<ListBean> listBean = new MutableLiveData<ListBean>();

    public MutableLiveData<ListBean> getListBean() {
        return listBean;
    }

    public Flowable<PagingData<ListBean.DatasBean>> rxLoadList() {
        return retroRepository.rxLoadList(this);
    }

}
