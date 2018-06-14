package com.sdl.moments.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.orhanobut.logger.Logger
import com.sdl.eyepetizer.net.NetClient
import com.sdl.moments.R
import com.sdl.moments.model.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_register.setOnClickListener{
            startActivityForResult(Intent(this@LoginActivity, RegisterActivity::class.java), 10000)
        }

        tv_login.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val user_name = et_input_username.text.toString()
        val user_psw = et_input_psw.text.toString()

        if(TextUtils.isEmpty(user_name)){
            Toast.makeText(this,resources.getString(R.string.str_input_username),Toast.LENGTH_SHORT).show()
            return
        }
        if(TextUtils.isEmpty(user_psw)){
            Toast.makeText(this,resources.getString(R.string.str_input_psw),Toast.LENGTH_SHORT).show()
            return
        }
        NetClient.loginByUsername(user_name,user_psw).subscribe({ userResult ->
            if (userResult.code == 0) {
                //登录成功，保存user到本地
                val user: User? = userResult.obj
                // TODO(不为空序列化保存到本地)

            } else {
                //登录失败，输出失败日志信息
                Logger.i(userResult.msg)
            }
        },{ throwable ->
            Logger.i(throwable.message ?: "")
        })
    }
}
