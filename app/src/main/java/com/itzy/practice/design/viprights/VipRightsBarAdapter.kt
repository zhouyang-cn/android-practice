package com.itzy.practice.design.viprights

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itzy.practice.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_vip_rights_detail.*
import org.jetbrains.anko.textColor

class VipRightsBarAdapter(
    private val rights: ArrayList<VipRights>,
    private val onItemClick: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<VipRightsBarAdapter.ViewHolder>() {

    var selectPosition = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vip_rights_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = rights.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setup(rights[position], position)
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun setup(vo: VipRights, position: Int) {
            tvName.text = vo.name
            val isSelected = position == selectPosition
            tvName.textColor = if (isSelected) {
                Color.parseColor("#efc986")
            } else {
                Color.parseColor("#999999")
            }
            ivAvatar.alpha = if (isSelected) 1.0f else 0.2f
            itemView.tag = position
            itemView.setOnClickListener {
                onItemClick?.invoke(it.tag as Int)
            }

        }
    }
}