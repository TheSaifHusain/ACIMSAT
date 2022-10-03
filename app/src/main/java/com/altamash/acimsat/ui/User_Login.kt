package com.altamash.acimsat.ui

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.altamash.acimsat.R
import com.altamash.acimsat.databinding.ActivityUserLoginBinding
import com.google.firebase.auth.FirebaseAuth

class User_Login : AppCompatActivity() {
    lateinit var binding: ActivityUserLoginBinding
    lateinit var auth: FirebaseAuth
    lateinit var sharedPrefrences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        binding = ActivityUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarGradiant(this)
        auth = FirebaseAuth.getInstance()
        //sharedPrefrences ka object banana
        sharedPrefrences = getSharedPreferences("User_Email", Context.MODE_PRIVATE)
        binding.userLogin.setOnClickListener {
            //sharedPrefrence me string add karna ye 4 ine
            val editor: SharedPreferences.Editor = sharedPrefrences.edit()
            val userEmailLogin = binding.userEmail.text.toString()
            editor.putString("Email", userEmailLogin)
            editor.apply()
            val userPasswordLogin = binding.userpassword.text.toString()
            if (userEmailLogin.trim().isEmpty() && userPasswordLogin.trim().isEmpty()) {
                Toast.makeText(this, "fill", Toast.LENGTH_SHORT).show()
            } else {

                auth.signInWithEmailAndPassword(userEmailLogin, userPasswordLogin)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Welcome to quiz :)", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(
                            applicationContext,
                            exception.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }

            }
        }

        binding.loginToSigUp.setOnClickListener {
            val i = Intent(this, Register::class.java)
            startActivity(i)
        }
        loginAlert()

    }

    private fun loginAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("** Note! **")
        builder.setMessage("Here Comes the Rule Before Singhn In")
        builder.setIcon(R.drawable.ic_baseline_admin_panel_settings_24)
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


