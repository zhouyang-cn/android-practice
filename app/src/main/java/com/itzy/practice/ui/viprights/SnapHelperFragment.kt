package com.itzy.practice.ui.viprights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.itzy.practice.R
import com.tmtpost.chaindd.ui.adapter.vip.VipRightsItemDecoration
import kotlinx.android.synthetic.main.fragment_snap_helper.*

class SnapHelperFragment : Fragment() {

    private var mRights = arrayListOf(
        VipRights("百度","","https://www.baidu.com/"),
        VipRights("链得得","","https://www.chaindd.com"),
        VipRights("Android","","https://developer.android.google.cn/"),
        VipRights("百度","","https://www.baidu.com/"),
        VipRights("链得得","","https://www.chaindd.com"),
        VipRights("Android","","https://developer.android.google.cn/"),
        VipRights("百度","","https://www.baidu.com/"),
        VipRights("链得得","","https://www.chaindd.com"),
        VipRights("Android","","https://developer.android.google.cn/")
    )
    private var mPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_snap_helper, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        barRecyclerView.apply {
            layoutManager = CenterLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            val barAdapter = VipRightsBarAdapter(mRights) { position ->
                mPosition = position
                this.smoothScrollToPosition(position)
                setPageNum(position)
                viewPager.setCurrentItem(position, true)
            }
            barAdapter.selectPosition = mPosition
            adapter = barAdapter
            val snapHelper = CenterSnapHelper { position ->
                mPosition = position
                setPageNum(position)
                barAdapter.selectPosition = position
                viewPager.setCurrentItem(position, true)
            }
            snapHelper.attachToRecyclerView(barRecyclerView)
            addItemDecoration(VipRightsItemDecoration())
        }
        viewPager.apply {
            adapter = MyAdapter(childFragmentManager, mRights)
            currentItem = mPosition
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(p0: Int) {
                }

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                }

                override fun onPageSelected(position: Int) {
                    mPosition = position
                    setPageNum(position)
                    barRecyclerView.smoothScrollToPosition(position)
                }

            })
        }

        tvPosition.text = "${mPosition + 1}"
        tvTotal.text = "/${mRights.size}"
    }

    fun setPageNum(position: Int) {
        tvPosition.text = "${position + 1}"
    }

    inner class MyAdapter(fm: FragmentManager, private val rights: ArrayList<VipRights>) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return VipRightsWebFragment.newInstance(rights[position].url)
        }

        override fun getCount() = rights.size

    }

}
