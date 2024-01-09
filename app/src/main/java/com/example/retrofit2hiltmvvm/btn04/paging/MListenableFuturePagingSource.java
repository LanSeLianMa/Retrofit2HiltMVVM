package com.example.retrofit2hiltmvvm.btn04.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.ListenableFuturePagingSource;
import androidx.paging.PagingState;

import com.example.retrofit2hiltmvvm.btn04.net.ApiService04;
import com.example.retrofit2hiltmvvm.btn04.viewmodel.Test04ViewModel;
import com.example.retrofit2hiltmvvm.common.bean.ListBean;
import com.example.retrofit2hiltmvvm.common.net.response.ResponseData;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.io.IOException;
import java.util.concurrent.Executors;

import retrofit2.HttpException;

/**
 * Guava + LiveData
 */
public class MListenableFuturePagingSource extends ListenableFuturePagingSource<Integer, ListBean.DatasBean> {

    private ApiService04 apiService04;
    private Test04ViewModel viewModel;
    public ListeningExecutorService mBgExecutor= MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public MListenableFuturePagingSource(ApiService04 apiService04, Test04ViewModel viewModel) {
        this.apiService04 = apiService04;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ListenableFuture<LoadResult<Integer, ListBean.DatasBean>> loadFuture(@NonNull LoadParams<Integer> params) {
        Integer nextPageNumber = params.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 1;
        }

        if (viewModel.isReset) {
            nextPageNumber = 1;
        }

        ListenableFuture<LoadResult<Integer, ListBean.DatasBean>> pageFuture =
                Futures.transform(apiService04.getList(nextPageNumber),
                        this::toLoadResult, mBgExecutor);

        ListenableFuture<LoadResult<Integer, ListBean.DatasBean>> partialLoadResultFuture =
                Futures.catching(pageFuture, HttpException.class,
                        LoadResult.Error::new, mBgExecutor);

        return Futures.catching(partialLoadResultFuture,
                IOException.class, LoadResult.Error::new, mBgExecutor);
    }

    private LoadResult<Integer, ListBean.DatasBean> toLoadResult(@NonNull ResponseData<ListBean> response) {
        viewModel.getListBean().postValue(response.getData());
        viewModel.isReset = false;
        return new LoadResult.Page<>(
                response.getData().getDatas(),
                null,
                response.getData().getCurPage(), // 下一页，自动累加
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, ListBean.DatasBean> state) {
        Integer anchorPosition = state.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, ListBean.DatasBean> anchorPage = state.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }
        return null;
    }

}
