package com.iitism.hackfestapp.ui.rules

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.iitism.hackfestapp.R
import com.iitism.hackfestapp.databinding.FragmentRulesBinding

class RulesFragment : Fragment() {

    private lateinit var binding: FragmentRulesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRulesBinding.inflate(inflater)

        binding.webview.webViewClient = WebViewClient()
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.setSupportZoom(true)
        binding.webview.loadUrl("https://drive.google.com/file/d/1oDBDm6A_WH420O46QHPTVK80wgHXgX9x/view?usp=share_link")

        return binding.root
    }

}