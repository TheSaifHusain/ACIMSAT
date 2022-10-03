package com.altamash.acimsat.ui

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.altamash.acimsat.R
import com.altamash.acimsat.databinding.ActivityAdminQuizBinding
import com.google.firebase.firestore.FirebaseFirestore

class AdminQuiz : AppCompatActivity() {
    private lateinit var binding: ActivityAdminQuizBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var maxQustion: String
    private var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_quiz)
        binding = ActivityAdminQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarGradiant(this)
        adminAlert(binding)
        db = FirebaseFirestore.getInstance()
        binding.submit.setOnClickListener {
            val qustion = binding.qustion.text.toString()
            val op1 = binding.adOp1.text.toString()
            val op2 = binding.adOp2.text.toString()
            val op3 = binding.adOp3.text.toString()
            val op4 = binding.adOp4.text.toString()
            val answer = binding.answer.text.toString()
            setMaxQuestion()
            setNoOfQuestion(binding, 1)
            val quizQuestion = hashMapOf(
                "Id" to index,
                "Question" to qustion,
                "Option1" to op1,
                "Option2" to op2,
                "Option3" to op3,
                "Option4" to op4,
                "Answer" to answer
            )
            if (
                binding.qustion.text?.trim().toString().isEmpty() &&
                binding.adOp1.text?.trim().toString().isEmpty() &&
                binding.adOp2.text?.trim().toString().isEmpty() &&
                binding.adOp3.text?.trim().toString().isEmpty() &&
                binding.adOp4.text?.trim().toString().isEmpty() &&
                binding.answer.text?.trim().toString().isEmpty()


            ) {

                Toast.makeText(this, "Please Fill All field", Toast.LENGTH_SHORT).show()
            } else {
                index++

                val quiz = db.collection("Quiz")
                quiz.document(index.toString()).set(quizQuestion)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Qustion Added", Toast.LENGTH_SHORT).show()
                        clearFeild(binding)
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(
                            applicationContext,
                            exception.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }

    }

    @SuppressLint("RestrictedApi")
    private fun adminAlert(binding: ActivityAdminQuizBinding) {
        clearFireStore()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("**Rule** \n **Application is Under-Development hai**")
        builder.setMessage("1. Maximum No. of Qustion Pahle input kare. \n 2. Ek baar me hi sare Qustion Submit kar den  \n 3. App ko close karne ke bad fir Admin Panel se Dubara Qustion Add na kare Warna question Field update ho jay gi.")
        builder.setCancelable(false)
        // Set up the input
        val input = EditText(this)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.hint = "Enter No. of Maximum Qustion"
        input.setBackgroundResource(R.drawable.back2)
        input.gravity = Gravity.CENTER
        builder.setView(input)

        // Giving Margin To editText in Alert
        builder.setView(input, 100, 50, 100, 50)
        // Set up the buttons
        builder.setPositiveButton("OK") { _, _ ->
            // Here you get get input text from the Edittext
            maxQustion = input.text.toString()
            setNoOfQuestion(binding, 0)
            Toast.makeText(this, maxQustion, Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Cancel") { _, _ ->
            finish()
        }

        builder.show()
    }

    private fun setMaxQuestion() {
        val indexR2: Int = index + 1
        if (indexR2.toString() == maxQustion) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("You Reached to MAX")

// Set up the buttons
            builder.setPositiveButton("OK") { _, _ ->
                // Here you get get input text from the Edittext
                finish()
            }
//            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            builder.show()
        }

    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sure Want to Exit")
// Set up the input
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text

// Set up the buttons
        builder.setPositiveButton("OK") { _, _ ->
            // Here you get get input text from the Edittext
            finish()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun clearFeild(binding: ActivityAdminQuizBinding) {
        binding.qustion.text?.clear()
        binding.adOp1.text?.clear()
        binding.adOp2.text?.clear()
        binding.adOp3.text?.clear()
        binding.adOp4.text?.clear()
        binding.answer.text?.clear()
    }

    @SuppressLint("SetTextI18n")
    private fun setNoOfQuestion(binding: ActivityAdminQuizBinding, indexR: Int) {
        binding.noOfQustion.text = "Qustion Added : ${index + indexR}/ $maxQustion"
    }

    private fun clearFireStore() {
        db = FirebaseFirestore.getInstance()

        for (i in 1..30) {
            db.collection("Quiz").document(i.toString()).delete().addOnSuccessListener {
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

