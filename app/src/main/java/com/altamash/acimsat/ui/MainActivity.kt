package com.altamash.acimsat.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.altamash.acimsat.R
import com.altamash.acimsat.announcement.announcement
import com.altamash.acimsat.databinding.ActivityMainBinding
import com.altamash.acimsat.otherTab.AboutUs
import com.altamash.acimsat.otherTab.ContactUs
import com.altamash.acimsat.pdfViewer.Books
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpvote(binding)
        imgCard(binding)

        sharedPreferences = getSharedPreferences("User_Email", Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("Email", null).toString()
        binding.title.text = "Salam! \n${userEmail.uppercase()} "
        //FirebaseMessaging.getInstance().subscribeToTopic("Alhumdulillah")

        setStatusBarGradiant(this)
        auth = FirebaseAuth.getInstance()
        binding.navigation.setNavigationItemSelectedListener {
            val admin = Intent(this, Admin_Login::class.java)
            val home = Intent(this, MainActivity::class.java)
            val quiz = Intent(this, MainQuiz::class.java)
            val contact = Intent(this, ContactUs::class.java)
            val book = Intent(this, Books::class.java)
            val about = Intent(this, AboutUs::class.java)
            val web = Intent(this, announcement::class.java)
            when (it.itemId) {
                R.id.admin -> startActivity(admin)
                R.id.Home -> startActivity(home)
                R.id.about -> startActivity(about)
                R.id.quiz -> startActivity(quiz)
                R.id.contact -> startActivity(contact)
                R.id.book -> startActivity(book)
                R.id.website -> startActivity(web)
            }
            true

        }

    }

    private fun setUpvote(binding: ActivityMainBinding) {
        setSupportActionBar(binding.topAppBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, binding.drawer,
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun setStatusBarGradiant(activity: Activity) {
        val window: Window = activity.window
        val background = ContextCompat.getDrawable(activity, R.drawable.blur)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, android.R.color.transparent)
        window.navigationBarColor =
            ContextCompat.getColor(activity, android.R.color.transparent)
        window.setBackgroundDrawable(background)
    }


    private fun imgCard(binding: ActivityMainBinding) {
        val loopImages = listOf(R.drawable.one, R.drawable.two, R.drawable.tree)
        val loopImages2 = listOf(R.drawable.imam, R.drawable.sis, R.drawable.kha)
        val loopImages3 = listOf(R.drawable.whiimam, R.drawable.whikha, R.drawable.whiquiz)
        val loopText = listOf(R.string.imam, R.string.sis, R.string.kha)
        val loopText2 = listOf(R.string.imam2, R.string.sis2, R.string.kha2)
        //fade_in animation
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        Handler(Looper.getMainLooper()).apply {
            val runnable = object : Runnable {
                var index = 0
                override fun run() {
                    //set animation
                    binding.img.startAnimation(animation)
                    binding.img2.startAnimation(animation)
                    binding.leaderImg.startAnimation(animation)
                    binding.leader.startAnimation(animation)
                    binding.leaderPos.startAnimation(animation)
                    //set Image
                    binding.img.setImageResource(loopImages[index])
                    binding.img2.setImageResource(loopImages3[index])
                    binding.leaderImg.setImageResource(loopImages2[index])
                    binding.leader.setText(loopText2[index])
                    binding.leaderPos.setText(loopText[index])
                    index++
                    if (index > loopImages.size - 1 && index > loopImages2.size - 1 && index > loopImages3.size - 1 && index > loopText.size - 1 && index > loopText2.size - 1) {
                        index = 0
                    }
                    postDelayed(this, 3000)
                }
            }
            postDelayed(runnable, 3000)
        }
    }
}


