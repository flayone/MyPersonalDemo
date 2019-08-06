package com.example.liyayu.myapplication.demoViews.loading_demo

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.demoViews.loading_demo.Gloading.STATUS_LOADING
import kotlinx.android.synthetic.main.view_special_loading.view.*
import org.jetbrains.anko.backgroundColor

/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/08/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class SpecialAdapter : Gloading.Adapter {
    override fun getView(holder: Gloading.Holder, convertView: View?, status: Int): View {
        var mConvertView = convertView
        if (status == STATUS_LOADING) {
            //only loading UI special
            val view: SpecialLoadingStatusView
            if (mConvertView == null || mConvertView !is SpecialLoadingStatusView) {
                view = SpecialLoadingStatusView(holder.context)
                mConvertView = view
            } else {
                view = mConvertView
            }
            view.start()
        } else {
            //other status use global UI
            val view: GlobalLoadingStatusView
            if (mConvertView == null || mConvertView !is GlobalLoadingStatusView) {
                view = GlobalLoadingStatusView(holder.context, holder.retryTask)
                mConvertView = view
            } else {
                view = mConvertView
            }
            view.setStatus(status)
        }
        return mConvertView
    }

    class SpecialLoadingStatusView :LinearLayout {

        private val ghostLoading by lazy { loading_anim }
        //    (context: Context)
        constructor(context: Context) : this(context, null )

        constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0 )
        constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
            if (isInEditMode) {
                return
            }
            initView(context)
        }

        private fun initView(context: Context){
            gravity = Gravity.CENTER
            backgroundColor = R.color.main_blue
            LayoutInflater.from(context).inflate(R.layout.view_special_loading, this, true)

        }

        override fun onDetachedFromWindow() {
            super.onDetachedFromWindow()
            ghostLoading.stopAnim()
        }

        fun start(){
            ghostLoading.startAnim(2500)
        }
    }
}