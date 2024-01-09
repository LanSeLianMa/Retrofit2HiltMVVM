package com.example.retrofit2hiltmvvm.common.net.response;

import com.example.retrofit2hiltmvvm.common.net.exception.ApiException;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ResponseTransformer<T> implements ObservableTransformer<ResponseData<T>, T> {
    public ResponseTransformer() {
    }

    public static <R> ResponseTransformer<R> obtain() {
        return new ResponseTransformer<>();
    }

    @NotNull
    @Override
    public ObservableSource<T> apply(@NotNull Observable<ResponseData<T>> upstream) {
        return upstream.onErrorResumeNext(new Function<Throwable, ObservableSource<? extends ResponseData<T>>>() {
            @Override
            public ObservableSource<? extends ResponseData<T>> apply(@NotNull Throwable throwable) throws Exception {
                return Observable.error(ApiException.handleException(throwable));
            }
        }).flatMap(new Function<ResponseData<T>, ObservableSource<T>>() {
            @Override
            public ObservableSource<T> apply(@NotNull ResponseData<T> responseData) throws Exception {
                // 请求成功，开始处理你的逻辑吧
                if (0 == responseData.getErrorCode()) {
                    if (null != responseData.getData()) {
                        return Observable.just(responseData.getData());
                    } else {
                        // 有可能存在返回的数据结果为ull，直接传Null会产生异常。
                        // 用过反射创建一个没有内容的数据实例。
                        return Observable.just(responseData.getData());
                    }
                }
                // 请求异常
                return Observable.error(new ApiException(responseData.getErrorCode(), responseData.getErrorMsg()));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}


