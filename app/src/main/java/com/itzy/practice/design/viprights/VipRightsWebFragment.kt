package com.itzy.practice.design.viprights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.itzy.practice.R
import kotlinx.android.synthetic.main.fragment_vip_rights_web.*

private const val ARG_URL = "url"

class VipRightsWebFragment : Fragment() {

    private var mUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vip_rights_web, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mUrl = it.getString(ARG_URL, "")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initWebSettings()
        if (mUrl.isNotEmpty()) {
            webView.loadUrl(mUrl)
        }
    }

    private fun initWebSettings() {
        webView.isVerticalScrollBarEnabled = false
        webView.settings.apply {
            useWideViewPort = true
            loadWithOverviewMode = true
            loadsImagesAutomatically = true
            javaScriptEnabled = true
            domStorageEnabled = true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
            VipRightsWebFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, url)
                }
            }
    }


}
