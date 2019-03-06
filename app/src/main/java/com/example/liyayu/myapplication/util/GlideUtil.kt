package com.example.liyayu.myapplication.util

import android.content.Context
import android.os.Looper
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import java.io.File
import java.math.BigDecimal

/**
 * glide加载网络图片
 * diskCacheOpen 是否使用本地缓存策略
 */


const val LOCAL_TEST_NET_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551072068129&di=bf1f421e030ce118133571962650b74c&imgtype=0&src=http%3A%2F%2Fs11.sinaimg.cn%2Fmw690%2F006UuO5Fzy7dEjSTRuaea%26690"

@JvmOverloads
fun glideLoadNetImage(context: Context, img: ImageView, url: String, diskCacheOpen: Boolean = true) {
    //RESULT 为只缓存图片最终展示的资源（比如原图1000*800 实际展示的是300*200）
    val diskChoice = if (diskCacheOpen) {
        DiskCacheStrategy.RESOURCE
    } else {
        DiskCacheStrategy.NONE
    }
    Glide.with(context)
            .load(url)
            .diskCacheStrategy(diskChoice)
            .centerCrop().into(img)
}

/**
 * 清除图片内存缓存
 */
fun clearMemoryCache(context: Context) {
    try {
//只能在主线程执行
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    Glide.get(context).clearMemory()
}

/**
 * 清除图片磁盘缓存
 */
fun clearImageDiskCache(context: Context) = try {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        startPoolThread(Runnable {
            Glide.get(context).clearDiskCache()
        })
    } else {
        Glide.get(context).clearDiskCache()
    }
} catch (e: Exception) {
    e.printStackTrace()
}

/**
 * 清除图片所有缓存
 */
fun clearImageAllCache(context: Context) {
    clearImageDiskCache(context)
    clearMemoryCache(context)
    val dir: String = context.externalCacheDir.absolutePath + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR
    deleteFolderFile(dir, true)
}

/**
 * 删除指定目录下的文件，这里用于缓存的删除
 *
 * @param filePath       filePath
 * @param deleteThisPath deleteThisPath
 */
private fun deleteFolderFile(filePath: String, deleteThisPath: Boolean) {
    if (!TextUtils.isEmpty(filePath)) {
        try {
            val file = File(filePath)
            if (file.isDirectory) {
                val files = file.listFiles()
                for (file1 in files) {
                    deleteFolderFile(file1.absolutePath, true)
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory) {
                    file.delete()
                } else {
                    if (file.listFiles().isEmpty()) {
                        file.delete()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


/**
 * 获取Glide造成的缓存大小
 *
 * @return CacheSize
 */
fun getCacheSize(context: Context): String {
    try {
        return getFormatSize(getFolderSize(File(context.cacheDir.absolutePath + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)).toDouble())
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

/**
 * 获取指定文件夹内所有文件大小的和
 *
 * @param file file
 * @return size
 * @throws Exception
 */
fun getFolderSize(file: File): Long {
    var size = 0L
    try {
        val fileList = file.listFiles()
        for (aFileList in fileList) {
            size = if (aFileList.isDirectory) {
                size + getFolderSize(aFileList)
            } else {
                size + aFileList.length()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return size
}

/**
 * 格式化单位
 *
 * @param size size
 * @return size
 */
private fun getFormatSize(size: Double): String {

    val kiloByte: Double = size / 1024
    if (kiloByte < 1) {
        return "$size Byte"
    }

    val megaByte = kiloByte / 1024
    if (megaByte < 1) {
        val result1 = BigDecimal(kiloByte)
        return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
    }

    val gigaByte = megaByte / 1024
    if (gigaByte < 1) {
        val result2 = BigDecimal(megaByte)
        return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
    }

    val teraBytes = gigaByte / 1024
    if (teraBytes < 1) {
        val result3 = BigDecimal(gigaByte)
        return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
    }
    val result4 = BigDecimal(teraBytes)

    return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
}

