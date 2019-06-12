package com.itzy.practice


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


/**
 * A simple [Fragment] subclass.
 *
 */
class CustomViewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        dashboardView.setScoreValue(75)
//        barChartView.setData(listOf(5, 10, 15, 20, 60, 18, 10, 2))
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_views, container, false)
    }


}
