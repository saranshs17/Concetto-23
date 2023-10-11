package com.iitism.concetto.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.iitism.concetto.MainActivity
import com.iitism.concetto.R
import com.iitism.concetto.databinding.FragmentWorkshopBinding
import com.iitism.concetto.ui.homefragment.HomeFragment
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class WorkshopFragment : Fragment() {

    var mprogressBar: ProgressBar? = null
    private lateinit var bindng : FragmentWorkshopBinding
    var pdf:String? = null
    private  var fl = 1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_workshop, container, false)
        bindng = FragmentWorkshopBinding.inflate(layoutInflater)
        fl = 0;
//        if (url != null) {
//            mwb_webView!!.settings.javaScriptEnabled = true
//            mwb_webView!!.settings.safeBrowsingEnabled = true
//
//            mwb_webView!!.webViewClient = object : WebViewClient() {
//                override fun onPageFinished(view: WebView?, url: String?) {
//                    super.onPageFinished(view, url)
//                    mwb_webView!!.visibility = View.VISIBLE
//                }
//            }
//            mwb_webView!!.settings.setSupportZoom(true)
//
//            try {
//                pdf = URLEncoder.encode(url, "UTF-8")
//            } catch (e: UnsupportedEncodingException) {
//                e.printStackTrace()
//            }
//
////            Toast.makeText(context, "$pdf", Toast.LENGTH_LONG).show()
//            mwb_webView!!.loadUrl("https://linktr.ee/Concetto_Workshops")
//        }
        goOn()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        val intent  = Intent(context,MainActivity::class.java)
        startActivity(intent)
    }
    fun goOn()
    {
        var url: String = "https://linktr.ee/Concetto_Workshops"
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            Log.i("pdflink", intent.data.toString())
            startActivity(intent)
        }
        catch (e : Exception)
        {
            Toast.makeText(context,"Browser not found",Toast.LENGTH_SHORT).show()
            Log.i("Exception",e.toString())
        }
    }


}
