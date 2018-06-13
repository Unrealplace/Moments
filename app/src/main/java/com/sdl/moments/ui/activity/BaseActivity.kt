package com.sdl.moments.ui.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.sdl.moments.R

/**
 * 描   述:基类activity
 * 创 建 人: gaodianjie
 * 创建日期: 2018/6/13 15:16
 */
open abstract class BaseActivity : AppCompatActivity() {
    protected var mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
 //       beforeSetContentView()
        // 在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //判断版本,如果[4.4,5.0)就设置状态栏和导航栏为透明
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT   ) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            //设置虚拟导航栏为透明
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
//        }
        // setContentView(getContentView())
        // initView()
        // initData()
    }

    //abstract fun initView()

    //abstract fun initData()

    /**在setContentView之前调用,比如setTheme*/
  //  fun beforeSetContentView() {}

    /**传入layout布局**/
    //abstract fun getContentView(): Int
}