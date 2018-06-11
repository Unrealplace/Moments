package com.sdl.moments.ui.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.sdl.moments.R
import com.sdl.moments.databinding.GridFilterImageBinding
import com.sdl.moments.util.DateUtil
import com.sdl.moments.util.ViewUtil
import java.io.File

class GridImageAdapter constructor(context: Context, list: MutableList<LocalMedia>): RecyclerView.Adapter<BindingViewHolder<ViewDataBinding>>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    private var list: MutableList<LocalMedia> = list

    private var context: Context = context
    /**
     * 默认为9
     */
    private val selectMax = 9

    companion object {
        val TYPE_CAMERA: Int = 1
        val TYPE_PICTURE: Int = 2
    }

    private lateinit var mOnItemClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mOnItemClickListener = listener
    }

    fun setList(list: MutableList<LocalMedia>) {
        this.list = list
    }

    override fun getItemViewType(position: Int): Int {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA
        }
        return TYPE_PICTURE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ViewDataBinding> {
        val viewDataBinding: ViewDataBinding = DataBindingUtil.inflate(mInflater, R.layout.grid_filter_image,parent,false)
        return BindingViewHolder(viewDataBinding)
    }

    override fun getItemCount(): Int {
        if (list.size < selectMax) {
            return list.size + 1
        }
        return list.size
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ViewDataBinding>, position: Int) {
        val gridFilterImageBinding: GridFilterImageBinding = holder.binding as GridFilterImageBinding
        if (getItemViewType(position) == TYPE_CAMERA) {
            gridFilterImageBinding.fiv.setImageResource(R.mipmap.addimg_1x)
            gridFilterImageBinding.fiv.setOnClickListener {
                mOnItemClickListener.onAddPicClick()
            }
            gridFilterImageBinding.llDel.visibility = View.INVISIBLE
        } else {
            gridFilterImageBinding.llDel.visibility = View.VISIBLE
            gridFilterImageBinding.llDel.setOnClickListener {
                val index = holder.adapterPosition
                if (index != RecyclerView.NO_POSITION) {
                    list.removeAt(index)
                    notifyItemRemoved(index)
                    notifyItemRangeChanged(index,list.size)
                }
            }
            val media: LocalMedia = list[position]
            val mimeType = media.mimeType
            var path = ""
            if (media.isCut && !media.isCompressed) {
                // 裁减过
                path = media.cutPath
            } else if (media.isCompressed || (media.isCut && media.isCompressed)) {
                // 压缩过
                path = media.compressPath
            } else {
                // 原图
                path = media.path
            }
            // 图片
            if (media.isCompressed) {
                Log.i("compress image result:", File(media.compressPath).length().div(1024).toString() + "k")
                Log.i("压缩地址::", media.compressPath)
            }

            Log.i("原图地址::", media.path)
            if (media.isCut) {
                Log.i("裁剪地址::", media.cutPath)
            }

            val pictureType = PictureMimeType.isPictureType(media.pictureType)
            val duration: Long = media.duration
            gridFilterImageBinding.tvDuration.visibility = if (pictureType == PictureConfig.TYPE_VIDEO) View.VISIBLE else View.GONE
            if (mimeType == PictureMimeType.ofAudio()) {
                gridFilterImageBinding.tvDuration.visibility = View.VISIBLE
                val drawable: Drawable? = ContextCompat.getDrawable(context,R.drawable.picture_audio)
                ViewUtil.modifyTextViewDrawable(gridFilterImageBinding.tvDuration,drawable!!,0)
                gridFilterImageBinding.fiv.setImageResource(R.drawable.audio_placeholder)
            } else {
                val drawable: Drawable? = ContextCompat.getDrawable(context,R.drawable.video_icon)
                ViewUtil.modifyTextViewDrawable(gridFilterImageBinding.tvDuration,drawable!!,0)
                val options = RequestOptions()
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(context)
                        .load(path)
                        .apply(options)
                        .into(gridFilterImageBinding.fiv)
            }
            gridFilterImageBinding.tvDuration.text = DateUtil.timeParse(duration)
            mOnItemClickListener.let { itemClickLister ->
                gridFilterImageBinding.root.setOnClickListener { v ->
                    val adapterPosition = holder.adapterPosition
                    itemClickLister.onItemClick(adapterPosition,v)
                }
            }
        }
    }

    fun isShowAddItem(position: Int): Boolean {
        var size: Int = if (list.size == 0) 0 else list.size
        return position == size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int,v: View)
        fun onAddPicClick()
    }

}