package com.iitism.concetto.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.iitism.concetto.R
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class WorkshopFragment : Fragment() {

    var mwb_webView: WebView? = null
    var mprogressBar: ProgressBar? = null
    var pdf:String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_workshop, container, false)

        mwb_webView = rootView.findViewById(R.id.wb_webView)
        mprogressBar = rootView.findViewById(R.id.progressBar)

        var url: String = "https://linktr.ee/Concetto_Workshops"
        if (url != null) {
            mwb_webView!!.settings.javaScriptEnabled = true
            mwb_webView!!.settings.safeBrowsingEnabled = true

            mwb_webView!!.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    mprogressBar!!.visibility = View.GONE
                    mwb_webView!!.visibility = View.VISIBLE
                }
            }
            mwb_webView!!.settings.setSupportZoom(true)

            try {
                pdf = URLEncoder.encode(url, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

//            Toast.makeText(context, "$pdf", Toast.LENGTH_LONG).show()
            mwb_webView!!.loadUrl("https://linktr.ee/Concetto_Workshops")
        }

        return rootView
    }
}
