package com.example.retrofit2hiltmvvm.btn03.net;

import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.example.retrofit2hiltmvvm.btn03.paging.MRxPagingSource;
import com.example.retrofit2hiltmvvm.btn03.viewmodel.Test03ViewModel;
import com.example.retrofit2hiltmvvm.common.bean.ListBean;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

/**
 * 请求接口数据
 */
public class Test03RequestRepository {

    @Inject
    ApiService03 apiService03;

    @Inject
    public Test03RequestRepository() {}

    public Flowable<PagingData<ListBean.DatasBean>> rxLoadList(Test03ViewModel viewModel) {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(viewModel);
        Pager<Integer, ListBean.DatasBean> pager = new Pager<>(
                new PagingConfig(20, // 每页数量
                        2, // 预加载，比如滚动到还剩2个的时候，开始加载下一页
                        false,
                        20), // 初始化数量
                () -> new MRxPagingSource(apiService03,viewModel));
        Flowable<PagingData<ListBean.DatasBean>> flowable = PagingRx.getFlowable(pager);
        return PagingRx.cachedIn(flowable, viewModelScope);
    }

}
