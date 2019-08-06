package com.example.liyayu.myapplication.baseFramework

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/07/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */

abstract class BaseRvAdapter<T>(private var list: MutableList<T>, @LayoutRes private val resourceId: Int) :
    RecyclerView.Adapter<MyViewHolder>() {
    open lateinit var mContext : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mContext = parent.context
        return MyViewHolder(View.inflate(parent.context, resourceId, null))
    }

    fun notifyData(data: MutableList<T>) {
        list = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    // 如果想要每次都调用onBindViewHolder()刷新item数据，就要重写getItemViewType()，让其返回position，否则很容易产生数据错乱的现象。
    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        onBind(holder.itemView, position, list[position])

    abstract fun onBind(holderView: View, position: Int, data: T)

}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


fun initRecycleLayoutManger(context: Context?): LinearLayoutManager {
    val layoutManager = object : LinearLayoutManager(context) {
        override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
            RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    layoutManager.orientation = RecyclerView.VERTICAL
    return layoutManager
}
