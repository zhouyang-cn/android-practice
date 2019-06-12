package com.itzy.practice


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_open_source_libs.*


/**
 * A simple [Fragment] subclass.
 *
 */
class OpenSourceLibsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open_source_libs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvFlycoTabLayout.setOnClickListener {
            findNavController().navigate(R.id.action_openSourceLibsFragment_to_flycoTabLayoutFragment)
        }
    }


}
