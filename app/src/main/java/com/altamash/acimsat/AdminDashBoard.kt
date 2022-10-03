package com.altamash.acimsat

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.altamash.acimsat.announcement.AdminAnnouncment
import com.altamash.acimsat.databinding.ActivityQuestionOptionBinding
import com.altamash.acimsat.ui.AdminQuiz
import com.altamash.acimsat.ui.AdminQuizSubjective
import com.altamash.acimsat.ui.AdminResult

class AdminDashBoard : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionOptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_option)
        binding = ActivityQuestionOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarGradiant(this)
        binding.annu.setOnClickListener {
            val i = Intent(this, AdminAnnouncment::class.java)
            startActivity(i)
        }
        binding.result.setOnClickListener {
            val i = Intent(this, AdminResult::class.java)
            startActivity(i)
        }
        binding.oblective.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("!!Warning!!")
            builder.setMessage(
                "1. Naya Quiz Add karne par \npurane saare data delete ho jy ge. " +
                        "\n 2. Ek baar me hi sare Qustion Submit kar den  "
            )
            builder.setPositiveButton("OK") { _, _ ->
                // Here you get get input text from the Edittext
                val i = Intent(this, AdminQuiz::class.java)
                startActivity(i)
            }
            builder.setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()

        }
        binding.subjective.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("!!Warning!!")
            builder.setMessage(
                "1. Naya Quiz Add karne par \npurane saare data delete ho jy ge. " +
                        "\n 2. Ek baar me hi sare Qustion Submit kar den  "
            )
            builder.setPositiveButton("OK") { _, _ ->
                // Here you get get input text from the Edittext
                val i = Intent(this, AdminQuizSubjective::class.java)
                startActivity(i)
            }
            builder.setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }
        adminAlert()
    }

    private fun adminAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("!!Warning!!")
        builder.setMessage(
            "1. Naya Quiz Add karne par \npurane saare data delete ho jy ge. " +
                    "\n 2. Ek baar me hi sare Qustion Submit kar den  "
        )
        builder.show()

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarGradiant(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity.window
            val background = ContextCompat.getDrawable(activity, R.drawable.gradient)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(activity, android.R.color.transparent)
            window.navigationBarColor =
                ContextCompat.getColor(activity, android.R.color.transparent)
            window.setBackgroundDrawable(background)
        }
    }
}