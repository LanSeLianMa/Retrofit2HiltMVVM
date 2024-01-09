package com.example.retrofit2hiltmvvm.btn03.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.example.retrofit2hiltmvvm.btn03.net.ApiService03;
import com.example.retrofit2hiltmvvm.btn03.viewmodel.Test03ViewModel;
import com.example.retrofit2hiltmvvm.common.bean.ListBean;
import com.example.retrofit2hiltmvvm.common.net.response.ResponseData;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * RxJava + Paging3
 */
public class MRxPagingSource extends RxPagingSource<Integer, ListBean.DatasBean> {

    private ApiService03 apiService03;
    private Test03ViewModel viewModel;

    public MRxPagingSource(ApiService03 apiService03, Test03ViewModel viewModel) {
        this.apiService03 = apiService03;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, ListBean.DatasBean>> loadSingle(@NonNull LoadParams<Integer> params) {
        Integer nextPageNumber = params.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 1;
        }

        if (viewModel.isReset) {
            nextPageNumber = 1;
        }

        return apiService03.getList(nextPageNumber)
                .subscribeOn(Schedulers.io())
                .map(this::toLoadResult)
                .onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<Integer, ListBean.DatasBean> toLoadResult(ResponseData<ListBean> response) {
        viewModel.getListBean().postValue(response.getData());
        viewModel.isReset = false;
        return new LoadResult.Page<>(
                response.getData().getDatas(),
                null,
                response.getData().getCurPage(), // 下一页，自动累加
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED
        );
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, ListBean.DatasBean> state) {

        Integer anchorPosition = state.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, ListBean.DatasBean> datasBeanPage = state.closestPageToPosition(anchorPosition);
        if (datasBeanPage == null) {
            return null;
        }

        Integer prevKey = datasBeanPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = datasBeanPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }

}
