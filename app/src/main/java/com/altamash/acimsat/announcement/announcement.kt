package com.altamash.acimsat.announcement

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.altamash.acimsat.R
import com.altamash.acimsat.databinding.ActivityAnnouncementBinding
import com.altamash.acimsat.ui.MainActivity
import com.google.firebase.firestore.CollectionReference


class announcement : AppCompatActivity() {
    lateinit var binding: ActivityAnnouncementBinding
    private lateinit var collectionReference: CollectionReference
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement)
        binding = ActivityAnnouncementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl("http://www.acimsat.unaux.com")
        binding.webView.settings.javaScriptEnabled=true
        binding.webView.refreshDrawableState()

        binding.webView.getSettings().setLoadsImagesAutomatically(true)
        binding.webView.getSettings().setJavaScriptEnabled(true)
        binding.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
        binding.webView.setWebViewClient(WebViewClient())




        MainActivity().setStatusBarGradiant(this)

    }
    inner class WebViewClient : android.webkit.WebViewClient() {

        // Load the URL
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        // ProgressBar will disappear once page is loaded
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding.progressBar.visibility = View.GONE
        }
    }
}