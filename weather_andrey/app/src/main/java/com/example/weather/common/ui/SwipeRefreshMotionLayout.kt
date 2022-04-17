package com.example.weather.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class SwipeRefreshMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (!isInteractionEnabled) {
            return
        }
        if (target is SwipeRefreshLayout) {
            val rv = target.children.firstOrNull { it is RecyclerView }
            rv?.let {
                val canScrollVertically = it.canScrollVertically(-1)
                if (dy < 0 && canScrollVertically) {
                    return
                }
            }
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type)
    }
}