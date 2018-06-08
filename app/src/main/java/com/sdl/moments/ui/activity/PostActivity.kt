package com.sdl.moments.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.sdl.moments.R
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {

    private lateinit var tags: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)


        initData()
    }

    private fun initData() {
        tags = resources.getStringArray(R.array.tags)
        for (i in 0 until tags.size) {
            flexBoxLayout.addView(getFlexBoxLayoutItemView(i))
        }
    }

    private fun getFlexBoxLayoutItemView(position: Int): View {
        val view: View = layoutInflater.inflate(R.layout.item_flex_box_layout, null, false)
        val itemTv: TextView = view.findViewById(R.id.text_tag)
        itemTv.text = tags[position]
        return view
    }

}
