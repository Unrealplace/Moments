package com.sdl.eyepetizer.net

import com.sdl.moments.model.BaseResult
import com.sdl.moments.model.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object NetClient {

    private val DEFAULT_TIMEOUT = 20L

    private var apiService: ApiService

    init {
        //创建一个OkHttpClient并设置超时时间
        var builder = OkHttpClient.Builder()
        builder.connectTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
        var retrofit = Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://173ur96139.iok.la:49037/moments/")
                .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    /**
     * 从服务端获取七牛token
     */
    fun getQiniuToken(): Observable<String> {
        return threadTransform(apiService.getFirstHomeData())
    }

    fun loginByUsername(username: String,password: String): Observable<BaseResult<User>> {
        return threadTransform(apiService.loginByUsername(username, password))
    }

    private fun <T> threadTransform(observable: Observable<T>) : Observable<T> {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}