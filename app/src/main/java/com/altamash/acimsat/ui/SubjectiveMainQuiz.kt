package com.altamash.acimsat.ui

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
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
import com.altamash.acimsat.databinding.ActivitySubjectiveMainQuizBinding
import com.google.firebase.firestore.FirebaseFirestore

class SubjectiveMainQuiz : AppCompatActivity() {
    private lateinit var binding: ActivitySubjectiveMainQuizBinding
    private lateinit var db: FirebaseFirestore
    private var index = 1
    lateinit var sharedPreferences3: SharedPreferences
    lateinit var q1: String
    lateinit var q2: String
    lateinit var q3: String
    lateinit var q4: String
    lateinit var q5: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjective_main_quiz)
        binding = ActivitySubjectiveMainQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setQuestion(binding)
        setStatusBarGradiant(this)
        //setResultInPref(binding)
        binding.next.setOnClickListener {
            if (binding.ansewer.text.toString().trim().isNotEmpty()) {
                index++
                setResultInPref(binding)
                setQuestion(binding)
                binding.ansewer.text?.clear()
            } else {
                Toast.makeText(this, "Please Fill The Answer.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setQuestion(binding: ActivitySubjectiveMainQuizBinding) {
        db = FirebaseFirestore.getInstance()
        db.collection("QuizSubjective").document(index.toString()).get()
            .addOnSuccessListener { task ->
                if (task.get("question").toString() != "null") {
                    binding.question.text = task.get("question").toString()
                } else {
                    Toast.makeText(this, "Quiz has been Completed!", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, Result::class.java)
                    startActivity(i)
                    finish()
                }


            }.addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are You Sure Want to Exit\nYour Progress will be lost.")
// Set up the input
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text

// Set up the buttons
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            finish()
        })
        builder.setNegativeButton(
            "Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun setResultInPref(binding: ActivitySubjectiveMainQuizBinding) {
        sharedPreferences3 = getSharedPreferences("Objective_Result", Context.MODE_PRIVATE)
        val editor = sharedPreferences3.edit()
        when (index - 1) {
            1 -> {
                q1 =
                    " Qustion 1:  ${binding.question.text}  \n  Answer: ${binding.ansewer.text.toString()}"
                editor.putString("question1", q1)
                editor.apply()
                Toast.makeText(this, "${index - 1}", Toast.LENGTH_SHORT).show()
            }
            2 -> {
                q2 =
                    " Qustion 2:  ${binding.question.text}  \n  Answer: ${binding.ansewer.text.toString()}"
                editor.putString("question2", q2)
                editor.apply()
                Toast.makeText(this, "${index - 1}", Toast.LENGTH_SHORT).show()
            }
            3 -> {
                q3 =
                    " Qustion 3:  ${binding.question.text}  \n  Answer: ${binding.ansewer.text.toString()}"
                editor.putString("question3", q3)
                editor.apply()
                Toast.makeText(this, "${index - 1}", Toast.LENGTH_SHORT).show()
            }
            4 -> {
                q4 =
                    " Qustion 4:  ${binding.question.text}  \n  Answer: ${binding.ansewer.text.toString()}"
                editor.putString("question4", q4)
                editor.apply()
                Toast.makeText(this, "${index - 1}", Toast.LENGTH_SHORT).show()
            }
            5 -> {
                q5 =
                    " Qustion 5:  ${binding.question.text}  \n  Answer: ${binding.ansewer.text.toString()}"
                editor.putString("question5", q5)
                editor.apply()
                Toast.makeText(this, "${index - 1}", Toast.LENGTH_SHORT).show()
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
