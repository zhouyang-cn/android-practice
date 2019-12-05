package com.itzy.practice.ui.motionlayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itzy.practice.R

/**
 * A simple [Fragment] subclass.
 */
class ExploreMotionLayoutPart1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore_motion_layout_part1, container, false)
    }

}
