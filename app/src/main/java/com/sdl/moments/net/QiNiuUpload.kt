package com.sdl.moments.net

import android.util.Log
import com.qiniu.android.storage.UploadManager
import com.orhanobut.logger.Logger
import com.qiniu.android.common.AutoZone
import com.qiniu.android.storage.Configuration
import com.sdl.eyepetizer.net.NetClient

object QiNiuUpload {

    private var uploadManager: UploadManager

    init {
        val config = Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .zone(AutoZone.autoZone)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build()
        uploadManager = UploadManager(config)
    }

    @JvmStatic
    fun upload(filePath: String) {
        NetClient.getQiniuToken().subscribe({token ->
            uploadManager.put(filePath, filePath.substring(filePath.lastIndexOf("/") + 1), token,
                    { key, info, res ->
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK) {
                            Logger.i("qiniu--->Upload Success")
                        } else {
                            Logger.i("qiniu--->Upload Fail")
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", "$key,\r\n $info,\r\n $res")
                    }, null)
        },{ throwable ->
            Logger.e(throwable.toString())
        })

    }

}