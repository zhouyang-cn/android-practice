package com.itzy.practice.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.itzy.practice.R
import com.itzy.practice.vo.CatalogVo
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val mList = listOf(
        CatalogVo(
            "MotionLayout",
            "MotionLayout实践",
            R.id.action_homeFragment_to_motionLayoutFragment
        ),CatalogVo(
            "吸顶效果",
            "使用CoordinatorLayout + AppBarLayout + NestedScrollView快速实现吸顶效果",
            R.id.action_catalogFragment_to_coordinatorLayoutFragment
        ),
        CatalogVo(
            "京东金融会员权益效果",
            "使用LinearSnapHelper + LinearLayoutManager + 自定义ItemDecoration来实现此效果",
            R.id.action_catalogFragment_to_snapHelperFragment
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = HomeAdapter(mList)

        }
    }

}
