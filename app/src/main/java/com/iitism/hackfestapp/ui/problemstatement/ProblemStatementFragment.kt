package com.iitism.hackfestapp.ui.problemstatement

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.iitism.hackfestapp.R
import com.iitism.hackfestapp.databinding.FragmentProblemStatementBinding
import kotlinx.coroutines.flow.combine

class ProblemStatementFragment : Fragment() {

    companion object {
        fun newInstance() = ProblemStatementFragment()
    }

    private lateinit var viewModel: ProblemStatementViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_problem_statement, container, false)
        val binding=FragmentProblemStatementBinding.bind(view)
        val sharedPreferences=this.activity?.getSharedPreferences("myPref",Context.MODE_PRIVATE)
        val url=sharedPreferences?.getString("problemStatement",null)
        Log.d("Prblemurl",url.toString())
        binding.WebView.webViewClient= WebViewClient()
        binding.WebView.settings.setSupportZoom(true)
        binding.WebView.settings.javaScriptEnabled = true
        binding.WebView.loadUrl("https://drive.google.com/file/d/1pb0pkA_juRKUedm5yUAlnxtYC1H6AmRA/view?usp=share_link"+url.toString())
        binding.WebView.clearSslPreferences()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProblemStatementViewModel::class.java)
        // TODO: Use the ViewModel
    }

}