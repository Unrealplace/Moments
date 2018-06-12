package com.sdl.eyepetizer.net

import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET("qiniu/token")
    fun getFirstHomeData(): Observable<String>


}