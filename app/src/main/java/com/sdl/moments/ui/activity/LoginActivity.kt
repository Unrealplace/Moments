package com.sdl.moments.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.sdl.moments.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_register.setOnClickListener(View.OnClickListener {
            (mContext as LoginActivity)!!.startActivityForResult(Intent(mContext!!, RegisterActivity::class.java), 10000)
        })

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

    }
}
