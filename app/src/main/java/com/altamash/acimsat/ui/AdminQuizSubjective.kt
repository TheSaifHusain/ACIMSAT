package com.altamash.acimsat.ui

import android.annotation.TargetApi
import android.app.Activity
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.altamash.acimsat.R
import com.altamash.acimsat.databinding.ActivityAdminQuizSubjectiveBinding
import com.google.firebase.firestore.FirebaseFirestore

class AdminQuizSubjective : AppCompatActivity() {
    private lateinit var binding: ActivityAdminQuizSubjectiveBinding
    private lateinit var db: FirebaseFirestore
    private var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_quiz_subjective)
        binding = ActivityAdminQuizSubjectiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarGradiant(this)
        db = FirebaseFirestore.getInstance()
        adminAlert()
        binding.submitButton.setOnClickListener {
            val subjectiveQuestion = binding.qustion.text.toString()
            val quizSubjectiveQuestion = hashMapOf(
                "question" to subjectiveQuestion,
            )
            if (binding.qustion.text?.trim().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter the Question", Toast.LENGTH_SHORT).show()
            } else if (index < 5) {
                index++
                binding.noOfSubjective.text = "Added Question : $index/5"
                val quiz = db.collection("QuizSubjective")
                quiz.document(index.toString()).set(quizSubjectiveQuestion)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Qustion Added", Toast.LENGTH_SHORT).show()
                        binding.qustion.text?.clear()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(
                            applicationContext,
                            exception.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
            } else {
                Toast.makeText(this, "Reached to max", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sure Want to Exit?.")
        // Set up the input
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        // Set up the buttons
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            finish()
        })
        builder.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun adminAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("**Rule** \n **Application is in Under-Development**")
        builder.setMessage("1. Maximum No. is 5 (five). \n 2. Ek baar me hi sare Qustion Submit kar den  \n 3. App ko close karne ke bad fir Admin Panel se Dubara Qustion Add na kare Warna question Field update ho jay gi.")
        builder.show()
        clearFireStore()

    }

    private fun clearFireStore() {
        db = FirebaseFirestore.getInstance()

        for (i in 1..10) {
            db.collection("QuizSubjective").document(i.toString()).delete().addOnSuccessListener {
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