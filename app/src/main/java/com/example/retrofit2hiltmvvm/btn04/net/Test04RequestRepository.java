package com.example.retrofit2hiltmvvm.btn04.net;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.example.retrofit2hiltmvvm.btn04.paging.MListenableFuturePagingSource;
import com.example.retrofit2hiltmvvm.btn04.viewmodel.Test04ViewModel;
import com.example.retrofit2hiltmvvm.common.bean.ListBean;

import javax.inject.Inject;

import kotlinx.coroutines.CoroutineScope;

/**
 * 请求接口数据
 */
public class Test04RequestRepository {

    @Inject
    ApiService04 apiService04;

    @Inject
    public Test04RequestRepository() {}

    public LiveData<PagingData<ListBean.DatasBean>> listenableFutureLoadList(Test04ViewModel viewModel) {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(viewModel);
        Pager<Integer, ListBean.DatasBean> pager = new Pager<>(
                new PagingConfig(20, // 每页数量
                        2, // 预加载，比如滚动到还剩2个的时候，开始加载下一页
                        false,
                        20), // 初始化数量
                () -> new MListenableFuturePagingSource(apiService04,viewModel));
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }

}
