package com.altamash.acimsat.ui

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.altamash.acimsat.R
import com.altamash.acimsat.databinding.ActivityResultBinding
import com.altamash.acimsat.model.UserLogout
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class Result : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var sharedPreferences2: SharedPreferences
    private lateinit var sharedPreferences3: SharedPreferences
    private lateinit var db: FirebaseFirestore

    //private lateinit var userCollectionRef:CollectionReference
    private lateinit var userName: String
    private lateinit var userPhone: String
    private lateinit var userCity: String
    private lateinit var userEmail: String
    lateinit var obResult: String
    lateinit var q1: String
    lateinit var q2: String
    lateinit var q3: String
    lateinit var q4: String
    lateinit var q5: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setStatusBarGradiant(this)
        setContentView(binding.root)
        //for getting user detail
        sharedPreferences2 = getSharedPreferences("User_Detail", Context.MODE_PRIVATE)
        //for objective Result
        sharedPreferences3 = getSharedPreferences("Objective_Result", Context.MODE_PRIVATE)
        //For Subjective Result
        q1 = sharedPreferences3.getString("question1", null).toString()
        q2 = sharedPreferences3.getString("question2", null).toString()
        q3 = sharedPreferences3.getString("question3", null).toString()
        q4 = sharedPreferences3.getString("question4", null).toString()
        q5 = sharedPreferences3.getString("question5", null).toString()
        //Objective Result
        obResult = sharedPreferences2.getString("Result", null).toString()
        //User Detail
        userName = sharedPreferences2.getString("Name", null).toString()
        userCity = sharedPreferences2.getString("City", null).toString()
        userPhone = sharedPreferences2.getString("Phone", null).toString()
        userEmail = sharedPreferences2.getString("Email", null).toString()

        //Save in ResultDetail data class

        //FireStore
        db = FirebaseFirestore.getInstance()
        val userDetailMap = hashMapOf(
            "Name" to userName,
            "City" to userCity,
            "Phone" to userPhone,
            "Email" to userEmail,
            "ObResult" to obResult,
            "Question1" to q1,
            "Question2" to q2,
            "Question3" to q3,
            "Question4" to q4,
            "Question5" to q5,
        )
        Toast.makeText(this, FieldValue.serverTimestamp().toString(), Toast.LENGTH_SHORT).show()
        db.collection("Result").document().set(userDetailMap).addOnSuccessListener {

        }

        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        //Handler(Looper.getMainLooper()).postDelayed({
        val logoutx = UserLogout()
        logoutx.logout()
        //Clear SharedPref
        //for clear user detail
        val sharedPreferences2: SharedPreferences =
            getSharedPreferences("User_Detail", Context.MODE_PRIVATE)
        val editor = sharedPreferences2.edit()
        editor.clear()
        editor.apply()
        //for clear Result
        val sharedPreferences3: SharedPreferences =
            getSharedPreferences("Objective_Result", Context.MODE_PRIVATE)
        val editorX = sharedPreferences3.edit()
        editorX.clear()
        editorX.apply()
        finish()
        // },5000)
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
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