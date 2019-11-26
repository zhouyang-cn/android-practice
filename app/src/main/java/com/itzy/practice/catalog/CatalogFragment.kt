package com.itzy.practice.catalog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.itzy.practice.R
import com.itzy.practice.vo.CatalogVo
import kotlinx.android.synthetic.main.fragment_catalog.*
class CatalogFragment : Fragment() {

    private val mList = listOf(
        CatalogVo(
         "cardCoordinatorLayout",
         "使用cardCoordinatorLayout实现吸顶效果",
            R.id.action_catalogFragment_to_coordinatorLayoutFragment
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CatalogAdapter(mList)

        }
    }

}
