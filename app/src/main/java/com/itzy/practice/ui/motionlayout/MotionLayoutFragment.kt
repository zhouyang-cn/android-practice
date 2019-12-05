package com.itzy.practice.ui.motionlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.itzy.practice.R
import kotlinx.android.synthetic.main.fragment_motion_layout.*

class MotionLayoutFragment : Fragment() {

    private val TAG = "MotionLayoutFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_motion_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnMotionText.setOnClickListener {
            findNavController().navigate(R.id.action_motionLayoutFragment_to_motionTextFragment)
        }
        button1.setOnClickListener {
            findNavController().navigate(R.id.action_explore_motion_layout_part1)
        }
    }

}
