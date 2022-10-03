package com.altamash.acimsat.pdfViewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altamash.acimsat.R
import com.pdfview.PDFView

class PdfView2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view2)
        findViewById<PDFView>(R.id.activity_main_pdf_view2).fromAsset("hu.pdf").show()
    }
}