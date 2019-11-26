package com.tmtpost.chaindd.ui.adapter.vip

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.dip


class VipRightsItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount
        val viewWidth = view.dip(102)
        val screenWidth = getScreenWidth(view.context)
        val margin = (screenWidth - viewWidth) / 2
        val leftMargin = if (position == 0) margin else 0
        val rightMargin = if (position == itemCount?.minus(1)) margin else 0
        outRect.set(leftMargin, 0, rightMargin, 0)
    }

    private fun getScreenWidth(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

}