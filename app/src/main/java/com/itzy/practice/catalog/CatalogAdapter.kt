package com.itzy.practice.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.itzy.practice.R
import com.itzy.practice.vo.CatalogVo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_catalog.*

class CatalogAdapter(
    private val list: List<CatalogVo>
) : RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun setup(vo: CatalogVo) {
            tvTitle.text = vo.title
            tvIntro.text = vo.intro
            itemView.setOnClickListener {
                itemView.findNavController().navigate(vo.action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_catalog, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setup(list[position])
    }
}