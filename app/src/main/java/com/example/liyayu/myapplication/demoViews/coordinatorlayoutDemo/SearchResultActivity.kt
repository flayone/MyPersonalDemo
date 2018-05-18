package com.example.liyayu.myapplication.demoViews.coordinatorlayoutDemo

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.widget.TextView
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.util.ToastUtil
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by liyayu on 2018/3/21.
 */

//注意在manifest中定义SearchableInf
class SearchResultActivity : BaseKotlinActivity() {
    private var showText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_activity_main)
        val intent = intent
        if (Intent.ACTION_SEARCH == intent.action) {
            showText = intent.getStringExtra(SearchManager.QUERY)
        }
        toolbar!!.setTitle(R.string.search_result)
        showSnackBar()
        find<TextView>(R.id.text_result).onClick { showSnackBar() }

    }

    private fun showSnackBar() {
        Snackbar.make(find(android.R.id.content), "搜索：" + showText, Snackbar.LENGTH_LONG)
                .setAction("Cancel", {
                    AlertDialog.Builder(this)
                            .setTitle("Title")
                            .setMessage("This is message")
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                                ToastUtil.showToast(this, "Cancel")
                            }
                            .setPositiveButton("Confirm") { dialog, _ ->
                                dialog.dismiss()
                                ToastUtil.showToast(this, "Confirm")
                            }
                            .create()
                            .show()
                }
                ).show()
    }
}