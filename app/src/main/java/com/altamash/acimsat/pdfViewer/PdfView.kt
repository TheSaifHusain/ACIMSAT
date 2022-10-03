package com.altamash.acimsat.pdfViewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.altamash.acimsat.R
import com.pdfview.PDFView

class PdfView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)
        findViewById<PDFView>(R.id.activity_main_pdf_view).fromAsset("eme.pdf").show()
    }

}