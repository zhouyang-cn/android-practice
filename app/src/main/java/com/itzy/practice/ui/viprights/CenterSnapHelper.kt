package com.itzy.practice.ui.viprights

import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class CenterSnapHelper(private val onSnapped: ((Int) -> Unit)? = null) : LinearSnapHelper() {

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        val view = super.findSnapView(layoutManager)
        view?.let {
            val position = layoutManager.getPosition(it)
            onSnapped?.invoke(position)
        }
        return view
    }

}