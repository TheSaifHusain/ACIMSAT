package com.altamash.acimsat.pdfViewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altamash.acimsat.R
import com.altamash.acimsat.databinding.ActivityAboutUsBinding
import com.altamash.acimsat.databinding.ActivityBooksBinding
import com.altamash.acimsat.ui.MainActivity

class Books : AppCompatActivity() {
    lateinit var binding: ActivityBooksBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)
        binding= ActivityBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.eme.setOnClickListener {
            val i=Intent(this,PdfView::class.java)
            startActivity(i)
        }
        binding.hu.setOnClickListener {
            val i=Intent(this,PdfView2::class.java)
            startActivity(i)
        }
        val grade=MainActivity()
        grade.setStatusBarGradiant(this)

    }
}