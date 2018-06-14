package com.sdl.moments.ui.activity

import android.util.Log
import com.sdl.moments.R
import kotlinx.android.synthetic.main.layout_toolbar.*
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_base_toolbar.*
import android.os.Bundle
import com.sdl.moments.R.id.root_layout


/**
 * 描   述:实现了Toolbar的Activity
 * 创 建 人: gaodianjie
 * 创建日期: 2018/6/13 15:16
 */
abstract class BaseToolbarActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.ToolbarActivityTheme)
        mContext = this
        // 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base_toolbar)
        initToolbar()
    }

    override fun setContentView(layoutId: Int) {
        setContentView(View.inflate(this, layoutId, null))
    }

    override fun setContentView(view: View) {
        Log.d("seToolbarActivity","BaseToolbarActivity");
        root_layout.addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar);
        supportActionBar!!.setDisplayShowTitleEnabled(false);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setHomeAsUpIndicator(R.mipmap.ic_back_black);
    }

    /**
     * 设置头部标题
     *
     * @param title
     */
    protected fun setToolBarTitle(title: CharSequence) {
        setToolBarTitle(title, true)
    }

    /**
     * 设置头部标题
     *
     * @param title        the title
     * @param showHomeAsUp the show home as up
     */
    protected fun setToolBarTitle(title: CharSequence, showHomeAsUp: Boolean) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(showHomeAsUp)
        val textView = TextView(this)
        textView.text = title
        textView.maxLines = 1
        textView.gravity = Gravity.CENTER
        textView.textSize = 18f
        textView.setTextColor(resources.getColor(R.color.color_text_555555))
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layout_center.removeAllViews()
        layout_center.addView(textView, layoutParams)
    }

    /**
     * 设置自定义头部标题
     *
     * @param view the view
     */
    protected fun setToolBarTitle(view: View) {
        setToolBarTitle(view, true)
    }

    /**
     * 设置自定义头部标题
     *
     * @param view         自定义标题View
     * @param showHomeAsUp 是否显示返回
     */
    protected fun setToolBarTitle(view: View, showHomeAsUp: Boolean) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(showHomeAsUp)
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layout_center.removeAllViews()
        layout_center.addView(view, layoutParams)
    }

    /**
     * 设置toolbar右边自定义视图
     *
     * @param view the view
     */
    protected fun setToolBarRight(view: View) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layout_right.removeAllViews()
        layout_right.addView(view, layoutParams)
    }

    protected fun setToolBarRight(title: CharSequence, listener: View.OnClickListener) {
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        val textView = TextView(mContext)
        textView.text = title
        textView.maxLines = 1
        textView.gravity = Gravity.CENTER
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.5f)
        textView.setTextColor(resources.getColor(R.color.color_text_555555))
        textView.setOnClickListener(listener)
        layout_right.removeAllViews()
        layout_right.addView(textView, layoutParams)
    }

    /**
     * 设置toolbar左边自定义视图
     *
     * @param view the view
     */
    protected fun setToolBarLeft(view: View) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layout_left.removeAllViews()
        layout_left.addView(view, layoutParams)
    }

    /**
     * 设置自定义toolbar
     *
     * @param view the view
     */
    protected fun setToolBar(view: View) {
        setToolBar(view, false)
    }

    /**
     * 设置自定义toolbar
     *
     * @param view         the view
     * @param showHomeAsUp the show home as up
     */
    protected fun setToolBar(view: View, showHomeAsUp: Boolean) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(showHomeAsUp)
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layout_center.removeAllViews()
        layout_center.addView(view, layoutParams)
    }

}