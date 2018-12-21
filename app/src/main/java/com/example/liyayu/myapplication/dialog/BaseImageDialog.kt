package com.example.liyayu.myapplication.dialog

import android.content.Context
import android.graphics.Bitmap
import com.example.liyayu.myapplication.R
import kotlinx.android.synthetic.main.dialog_image.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by liyayu on 2018/12/20.
 */
class BaseImageDialog(context: Context) : BaseKtDialog(context) {
    override fun initView() = setContentView(R.layout.dialog_image)

    class Bu(val context: Context, val imgResource: Bitmap) {
        fun create(): BaseImageDialog {
            val dialog = BaseImageDialog(context)
            dialog.run {
                img.setImageBitmap(imgResource)
                close.onClick {
                    dismiss()
                }
            }
            return dialog
        }

    }
}