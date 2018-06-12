package com.sdl.moments.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.TextView
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.orhanobut.logger.Logger
import com.sdl.moments.R
import com.sdl.moments.net.QiNiuUpload
import com.sdl.moments.ui.adapter.FullyGridLayoutManager
import com.sdl.moments.ui.adapter.GridImageAdapter
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : BaseActivity(),GridImageAdapter.OnItemClickListener {

    private lateinit var tags: Array<String>

    private var selectList = ArrayList<LocalMedia>()

    private lateinit var imageAdapter: GridImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        initView()
        initData()
    }

    private fun initView() {
        val manager = FullyGridLayoutManager(
                this,4, GridLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = manager
        imageAdapter = GridImageAdapter(this,selectList)
        imageAdapter.setOnItemClickListener(this)
        recyclerView.adapter = imageAdapter
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

    override fun onItemClick(position: Int, v: View) {
        if (selectList.size > 0) {
            val media: LocalMedia = selectList[position]
            val pictureType: String = media.pictureType
            val mediaType = PictureMimeType.pictureToVideo(pictureType)
            when (mediaType) {
                PictureConfig.TYPE_IMAGE -> {
                    PictureSelector.create(this).themeStyle(R.style.picture_default_style).openExternalPreview(position,selectList)
                }
                PictureConfig.TYPE_VIDEO -> {
                    PictureSelector.create(this).externalPictureVideo(media.path)
                }
                PictureConfig.TYPE_AUDIO -> {
                    PictureSelector.create(this).externalPictureAudio(media.path)
                }
            }
        }
    }

    override fun onAddPicClick() {
        // 默认进入相册
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAll())
                .theme(R.style.picture_default_style)
                .maxSelectNum(9)
                .minSelectNum(1)
                .imageSpanCount(4) //每行显示个数
                .selectionMode(PictureConfig.MULTIPLE) // 多选 or 单选
                .previewImage(true) // 是否可预览图片
                .previewVideo(true) // 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(true) // 是否显示拍照按钮
                .isZoomAnim(true) // 图片列表点击 缩放效果 默认true
                .setOutputCameraPath("/moments") // 自定义拍照保存路径
                .enableCrop(false) //不需要裁剪
                .compress(true) // 是否压缩
                .synOrAsy(true) // 同步true或异步false压缩 默认同步
                .glideOverride(160,160) // glide加载宽高,越小列表越流畅,但会影响图片质量
                .withAspectRatio(1,1) // 裁剪比例
                .hideBottomControls(false) // 是否显示uCrop工具栏,默认不显示
                .isGif(true) // 是否显示gif图片
                .freeStyleCropEnabled(true) // 裁剪框是否可拖拽
                .circleDimmedLayer(false) //是否圆形裁剪
                .showCropFrame(true) // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true) // 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false) // 是否开启点击声音
                .selectionMedia(selectList) // 是否传入已选图片
                .minimumCompressSize(100) // 小于100kB的图片不压缩
                .rotateEnabled(true) // 裁剪是否可旋转图片
                .scaleEnabled(true) //裁剪是否可缩放图片
                .recordVideoSecond(10) //录制视频秒数
                .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    selectList = PictureSelector.obtainMultipleResult(data) as ArrayList<LocalMedia>
                    for (media in selectList) {
                        Logger.i("picture---> " + media.path)
                    }
                    imageAdapter.setList(selectList)
                    imageAdapter.notifyDataSetChanged()
                }
            }
            Thread{
                for (media in selectList) {
                    QiNiuUpload.upload(media.path)
                }
            }.start()
        }
    }
}
