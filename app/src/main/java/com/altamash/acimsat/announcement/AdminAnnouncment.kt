package com.altamash.acimsat.announcement

import android.annotation.TargetApi
import android.app.Activity
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.altamash.acimsat.R
import com.altamash.acimsat.databinding.ActivityAdminAnnouncmentBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class AdminAnnouncment : AppCompatActivity() {
    private lateinit var binding: ActivityAdminAnnouncmentBinding
    private lateinit var db: FirebaseFirestore
    lateinit var title: String
    lateinit var detail: String
    lateinit var link: String
    private lateinit var mainDate: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_announcment)
        binding = ActivityAdminAnnouncmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarGradiant(this)
        binding.date.setOnClickListener { selectDate() }
        binding.annuBtn.setOnClickListener { setToFireStore(binding) }


    }

    private fun setToFireStore(binding: ActivityAdminAnnouncmentBinding) {
        title = binding.title.text.toString()
        detail = binding.detail.text.toString()
        link = binding.link.text.toString()
        binding.title.text?.clear()
        binding.detail.text?.clear()
        binding.link.text?.clear()
        val annuMap = hashMapOf(
            "title" to title,
            "deatil" to detail,
            "link" to link,
            "date" to mainDate
        )
        db = FirebaseFirestore.getInstance()
        val annuDb = db.collection("AnnuColl")
        annuDb.document().set(annuMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Annu Added", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    applicationContext,
                    exception.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun selectDate() {
        val date = Calendar.getInstance()
        val day = date.get(Calendar.DAY_OF_MONTH)
        val month = date.get(Calendar.MONTH)
        val year = date.get(Calendar.YEAR)
        DatePickerDialog(this, { _, year, month, day ->
            mainDate = "$day/${month + 1}/$year"
            Toast.makeText(this, mainDate, Toast.LENGTH_SHORT).show()
        }, year, month, day).show()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarGradiant(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity.window
            val background = ContextCompat.getDrawable(activity, R.drawable.gradient)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor =
                ContextCompat.getColor(activity, android.R.color.transparent)
            window.navigationBarColor =
                ContextCompat.getColor(activity, android.R.color.transparent)
            window.setBackgroundDrawable(background)
        }
    }
}
