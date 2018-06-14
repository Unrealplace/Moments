package com.sdl.moments.model

data class User(val id: Long
                , val username: String
                , var password: String
                , var nickname: String
                , var birthday: Long
                , var mobile: String
                , var province: String
                , var region: String
                , var integral: Int
                , var avatar: String
                , var salt: String)