package com.altamash.acimsat.ui

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.altamash.acimsat.AdminDashBoard
import com.altamash.acimsat.R
import com.altamash.acimsat.databinding.ActivityAdminLoginBinding

class Admin_Login : AppCompatActivity() {
    lateinit var binding: ActivityAdminLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarGradiant(this)
        binding.adminlogin.setOnClickListener {
            val email = binding.adminEmail.text.toString()
            val password = binding.adminPassword.text.toString()
            if (email.trim().isEmpty() && password.trim().isEmpty()) {
                Toast.makeText(this, "fill", Toast.LENGTH_SHORT).show()
            } else {
                if (email == "adminaltamash@313.com" && password == "Altamash#1") {
                    val intent = Intent(this, AdminDashBoard::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Welcome Altamash Bhai! :)", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarGradiant(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity.window
            val background = ContextCompat.getDrawable(activity, com.altamash.acimsat.R.drawable.gradient)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(activity, android.R.color.transparent)
            window.navigationBarColor =
                ContextCompat.getColor(activity, android.R.color.transparent)
            window.setBackgroundDrawable(background)
        }
    }
}