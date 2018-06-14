package com.sdl.eyepetizer.net

import com.sdl.moments.model.BaseResult
import com.sdl.moments.model.User
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("qiniu/token")
    fun getFirstHomeData(): Observable<String>

    @FormUrlEncoded
    @POST("user/login/username")
    fun loginByUsername(@Field("username") username: String
                        ,@Field("password") password: String): Observable<BaseResult<User>>
}