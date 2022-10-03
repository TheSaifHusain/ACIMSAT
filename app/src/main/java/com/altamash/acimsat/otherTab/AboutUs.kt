package com.altamash.acimsat.otherTab

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.altamash.acimsat.R
import com.altamash.acimsat.databinding.ActivityAboutUsBinding
import com.altamash.acimsat.ui.MainActivity
import com.altamash.acimsat.ui.User_Login
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class AboutUs : AppCompatActivity() {
    lateinit var binding: ActivityAboutUsBinding
    var index = 0
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //load Gif using Glide
        Glide.with(this).load(R.drawable.back).into(binding.imageView);
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            Toast.makeText(this, "User Already Signed In !", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.next.setOnClickListener {
            index++
            updateText(binding)
        }
        binding.title.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.login.setOnClickListener {
            val i = Intent(this, User_Login::class.java)
            startActivity(i)
        }
        //val g = MainActivity()
        //g.setStatusBarGradiant(this)


    }

    private fun updateText(binding: ActivityAboutUsBinding) {
        when (index) {
            1 -> {
                binding.title.text = getString(R.string.waw)
                binding.desc.text = getString(R.string.whoarewe)
            }
            2 -> {
                binding.title.text = getString(R.string.OurMission)
                binding.desc.text = getString(R.string.ourmission)
            }
            3 -> {
                binding.title.text = getString(R.string.what_we_do)
                binding.desc.text = getString(R.string.what_we_do)
            }
        }
    }
}