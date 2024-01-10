package com.example.retrofit2hiltmvvm.common.net;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.retrofit2hiltmvvm.btn01.net.ApiService01;
import com.example.retrofit2hiltmvvm.btn02.net.ApiService02;
import com.example.retrofit2hiltmvvm.btn03.net.ApiService03;
import com.example.retrofit2hiltmvvm.btn04.net.ApiService04;
import com.example.retrofit2hiltmvvm.common.net.baseurlfactory.CallFactoryProxy;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.guava.GuavaCallAdapterFactory;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class RequestManager {

    // ========================= 每个模块的Api =========================

    @Singleton
    @Provides
    ApiService04 test04ApiService(Retrofit retrofit) {
        return retrofit.create(ApiService04.class);
    }

    @Singleton
    @Provides
    ApiService03 test03ApiService(Retrofit retrofit) {
        return retrofit.create(ApiService03.class);
    }

    @Singleton
    @Provides
    ApiService02 test02ApiService(Retrofit retrofit) {
        return retrofit.create(ApiService02.class);
    }

    /**
     * @param retrofit 这个retrofit 就是 getRetrofitInstance()，自动扫描赋值
     * @return
     */
    @Singleton
    @Provides
    ApiService01 test01ApiService(Retrofit retrofit) {
        return retrofit.create(ApiService01.class);
    }

    // ================================================================


    private final String baseUrl = "https://www.wanandroid.com";

    private final int CONNECTION_TIME_OUT = 10; // 连接超时时间
    private final int WRITE_TIME_OUT = 10; // 写入超时时间
    private final int READ_TIME_OUT = 10; // 读取超时时间


    @Singleton
    @Provides
    OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Log.i("TAG", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * @param client 这个client 就是 getOkHttpClient()，自动扫描赋值
     * @return
     */
    @Singleton
    @Provides
    Retrofit getRetrofitInstance(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client) // 可以不设置，Retrofit会自动生成一个client
                .callFactory(new CallFactoryProxy((Call.Factory) client) { // 动态设置 baseUrl，注意：要写在 .client(client) 后面，不然会被覆盖掉，导致无效
                    @Nullable
                    @Override
                    protected HttpUrl getNewUrl(String baseUrlName, Request request) {
                        if (baseUrlName.equals("baidu")) {
                            String oldUrl = request.url().toString();
                            String newUrl = oldUrl.replace(baseUrl, "https://www.baidu.com/");
                            return HttpUrl.get(newUrl);
                        }
                        return null;
                    }
                })
                // 增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                // 增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                // 增加返回值为Observable<T>的支持
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                // 增加返回值为ListenableFuture<T>的支持
                .addCallAdapterFactory(GuavaCallAdapterFactory.create())
                .build();
    }

}
