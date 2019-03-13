class MyRecycleViewItemDecoration(private val mSpace: Float, private val mEdgeSpace: Float) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val manager = parent.layoutManager
        val childPosition = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount
        if (manager != null && itemCount != null) {
            if (manager is GridLayoutManager) {
                // manager为GridLayoutManager时
                setGridOffset(manager.orientation, manager.spanCount, outRect, childPosition, itemCount)
            } else if (manager is LinearLayoutManager) {
                // manager为LinearLayoutManager时
                setLinearOffset(manager.orientation, outRect, childPosition, itemCount)
            }
        }
    }

    private fun setGridOffset(orientation: Int, spanCount: Int, outRect: Rect, childPosition: Int, itemCount: Int) {
        val totalSpace = mSpace * (spanCount - 1) + mEdgeSpace * 2
        val eachSpace = totalSpace / spanCount
        val column = childPosition % spanCount
        val row = childPosition / spanCount

        var left: Float
        var right: Float
        var top: Float
        var bottom: Float
        if (orientation == GridLayoutManager.VERTICAL) {
            top = 0f
            bottom = mSpace

            if (childPosition < spanCount) {
                top = mEdgeSpace
            }
            if (itemCount % spanCount != 0 && itemCount / spanCount == row || itemCount % spanCount == 0 && itemCount / spanCount == row + 1) {
                bottom = mEdgeSpace
            }

            if (spanCount == 1) {
                left = mEdgeSpace
                right = left
            } else {
                left = column * (eachSpace - mEdgeSpace - mEdgeSpace) / (spanCount - 1) + mEdgeSpace
                right = eachSpace - left
            }
        } else {
            left = 0f
            right = mSpace

            if (childPosition < spanCount) {
                left = mEdgeSpace
            }
            if (itemCount % spanCount != 0 && itemCount / spanCount == row || itemCount % spanCount == 0 && itemCount / spanCount == row + 1) {
                right = mEdgeSpace
            }

            if (spanCount == 1) {
                top = mEdgeSpace
                bottom = top
            } else {
                top = column * (eachSpace - mEdgeSpace - mEdgeSpace) / (spanCount - 1) + mEdgeSpace
                bottom = eachSpace - top
            }
        }
        outRect.set(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
    }

    private fun setLinearOffset(orientation: Int, outRect: Rect, childPosition: Int, itemCount: Int) {
        val mEdgeSpace = mEdgeSpace.toInt()
        val mSpace = mSpace.toInt()
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            when (childPosition) {
                0 -> outRect.set(mEdgeSpace, 0, mSpace, 0)// 第一个要设置PaddingLeft
                itemCount - 1 -> outRect.set(0, 0, mEdgeSpace, 0)// 最后一个设置PaddingRight
                else -> outRect.set(0, 0, mSpace, 0)
            }
        } else {
            when (childPosition) {
                0 -> outRect.set(0, mEdgeSpace, 0, mSpace)// 第一个要设置PaddingTop
                itemCount - 1 -> outRect.set(0, 0, 0, mEdgeSpace)// 最后一个要设置PaddingBottom
                else -> outRect.set(0, 0, 0, mSpace)
            }
        }
    }
}