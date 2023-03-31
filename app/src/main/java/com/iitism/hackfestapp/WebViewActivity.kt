package com.iitism.hackfestapp

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.web_view)
        val webView:WebView=findViewById(R.id.webView)
        webView.webViewClient= WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true
        val sharedPreferences=getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val url=sharedPreferences.getString("problemStatement",null).toString()
        Log.d("web",url)
        webView.loadUrl("https://drive.google.com/file/d/1pb0pkA_juRKUedm5yUAlnxtYC1H6AmRA/view?usp=share_link"+url)
        webView.clearSslPreferences()
    }
}