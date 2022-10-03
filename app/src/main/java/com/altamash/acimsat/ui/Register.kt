package com.altamash.acimsat.ui

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.altamash.acimsat.R
import com.altamash.acimsat.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarGradiant(this)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        binding.register.setOnClickListener {
            val name = binding.name.text.toString()
            val phone = binding.phoneNo.text.toString()
            val city = binding.city.text.toString()
            val confirmPassword = binding.rgConfirmPassword.text.toString()
            val rgemail = binding.rgEmail.text.toString()
            val rgpassword = binding.rgPassword.text.toString()
            val user = hashMapOf(
                "Name" to name,
                "Phone" to phone,
                "City" to city,
                "Email" to rgemail
            )

            if (rgemail.trim().isEmpty() && rgpassword.trim().isEmpty()) {
                Toast.makeText(this, "fill", Toast.LENGTH_SHORT).show()
            } else {
                if (rgpassword == confirmPassword) {
                    val users = db.collection("USERS")
                    val query = users.whereEqualTo("Email", rgemail).get()
                        .addOnSuccessListener {
                            if (it.isEmpty) {
                                auth.createUserWithEmailAndPassword(rgemail, rgpassword)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            users.document(rgemail).set(user)
                                            Toast.makeText(
                                                this,
                                                "User Created Now Sign In",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                            val intent = Intent(this, User_Login::class.java)
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

                } else {
                    Toast.makeText(this, "Password Does't Match", Toast.LENGTH_SHORT).show()
                }
            }
        }
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