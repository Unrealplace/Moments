package com.sdl.moments.model

class BaseResult<T> {

    var code: Int = 0

    var msg: String = ""

    var obj: T? = null
}