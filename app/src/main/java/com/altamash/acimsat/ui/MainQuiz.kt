package com.altamash.acimsat.ui

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.altamash.acimsat.R
import com.altamash.acimsat.databinding.ActivityMainQuizBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainQuiz : AppCompatActivity() {
    private lateinit var binding: ActivityMainQuizBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var index = 1
    private var newIndex = 1
    private var rightIndex = 0
    private var wrongIndex = 0
    private lateinit var getOption: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferences2: SharedPreferences
    lateinit var name: String
    lateinit var city: String
    private lateinit var phone: String
    private lateinit var result: String
    private lateinit var userEmail: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarGradiant(this)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        defultOptionStyle(binding)
        updateTextView(binding)
        setUserName(binding)
        if (auth.currentUser != null) {
            setData(binding)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            //Do something here
            binding.next.setOnClickListener {
                if (newIndex > 1) {
                    answerChecking()
                    //Toast.makeText(this, "\"Total Question :${rightIndex + wrongIndex} \n Wrong Answer : $wrongIndex \n Right Answer : $rightIndex \"", Toast.LENGTH_SHORT).show()
                    defultOptionStyle(binding)
                    index++
                    setData(binding)
                } else {
                    Toast.makeText(this, "Please Choose any", Toast.LENGTH_SHORT).show()
                }
            }
        }, 800)

    }

    private fun setData(binding: ActivityMainQuizBinding) {
        newIndex = 1
        db = FirebaseFirestore.getInstance()
        db.collection("Quiz").document(index.toString()).get()
            .addOnSuccessListener { task ->
                if (task.get("Question").toString() != "null") {
                    binding.qustion.text = task.get("Question").toString()
                    binding.op1.text = task.get("Option1").toString()
                    binding.op2.text = task.get("Option2").toString()
                    binding.op3.text = task.get("Option3").toString()
                    binding.op4.text = task.get("Option4").toString()
                } else {

                    result =
                        "Total Question :${rightIndex + wrongIndex} \n Wrong Answer : $wrongIndex \n Right Answer : $rightIndex "
                    sharedPreferences2 = getSharedPreferences("User_Detail", Context.MODE_PRIVATE)
                    val editor = sharedPreferences2.edit()
                    editor.putString("Result", result)
                    editor.apply()
                    // Toast.makeText(this, result,Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, SubjectiveMainQuiz::class.java)
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

    private fun defultOptionStyle(binding: ActivityMainQuizBinding) {
        val qustionList: ArrayList<TextView> = arrayListOf()
        qustionList.add(0, binding.op1)
        qustionList.add(1, binding.op2)
        qustionList.add(2, binding.op3)
        qustionList.add(3, binding.op4)

        for (op in qustionList) {
            op.setTextColor(Color.parseColor("#000000"))
            op.background = ContextCompat.getDrawable(this, R.drawable.back2)
            op.typeface = Typeface.DEFAULT
        }
    }

    private fun updatedOptionStyle(binding: ActivityMainQuizBinding, view: TextView) {
        defultOptionStyle(binding)
        view.background = ContextCompat.getDrawable(this, R.drawable.selectedoption)
        view.typeface = Typeface.DEFAULT_BOLD

    }

    private fun updateTextView(binding: ActivityMainQuizBinding) {
        binding.op1.setOnClickListener {
            getOption = binding.op1.text.toString()
            updatedOptionStyle(binding, binding.op1)
            newIndex++
        }
        binding.op2.setOnClickListener {
            getOption = binding.op2.text.toString()
            updatedOptionStyle(binding, binding.op2)
            newIndex++
        }
        binding.op3.setOnClickListener {
            getOption = binding.op3.text.toString()
            updatedOptionStyle(binding, binding.op3)
            newIndex++
        }
        binding.op4.setOnClickListener {
            getOption = binding.op4.text.toString()
            updatedOptionStyle(binding, binding.op4)
            newIndex++
        }

    }

    private fun answerChecking() {

        db = FirebaseFirestore.getInstance()
        db.collection("Quiz").document(index.toString()).get()
            .addOnSuccessListener { task ->
                val answer = task.get("Answer").toString()
                if (getOption == answer) {
                    rightIndex++
                } else {
                    wrongIndex++
                }

            }.addOnFailureListener { exception ->
                Toast.makeText(
                    applicationContext,
                    exception.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sure Want to Exit \nYoue Progress will be lost.")
// Set up the input
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text

// Set up the buttons
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
            // Here you get get input text from the Edittext
            finish()
        })
        builder.setNegativeButton(
            "Cancel",
            DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })

        builder.show()
    }

    private fun setUserName(binding: ActivityMainQuizBinding) {
        db = FirebaseFirestore.getInstance()
        //sharedPrefrences se data lena
        sharedPreferences = getSharedPreferences("User_Email", Context.MODE_PRIVATE)
        userEmail = sharedPreferences.getString("Email", null).toString()
        Toast.makeText(this, userEmail, Toast.LENGTH_SHORT).show()
        db.collection("USERS").document(userEmail.toString()).get()
            .addOnSuccessListener {
                name = it.get("Name").toString().uppercase()
                city = it.get("City").toString().uppercase()
                phone = it.get("Phone").toString().uppercase()
                binding.userName.text = "Welcome One of 313/$name"
                sharedPreferences2 = getSharedPreferences("User_Detail", Context.MODE_PRIVATE)
                val editor = sharedPreferences2.edit()
                editor.putString("Name", name)
                editor.putString("City", city)
                editor.putString("Phone", phone)
                editor.putString("Email", userEmail)
                editor.apply()
            }

    }

//    private fun userLogOut() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Want to Logout?.")
//        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
//            val logoutx = UserLogout()
//            logoutx.logout()
//            //Clear SharedPref
//            //for clear user detail
//            val sharedPreferences2: SharedPreferences =
//                getSharedPreferences("User_Detail", Context.MODE_PRIVATE)
//            val editor = sharedPreferences2.edit()
//            editor.clear()
//            editor.apply()
//            //for clear Result
//            val sharedPreferences3: SharedPreferences =
//                getSharedPreferences("Objective_Result", Context.MODE_PRIVATE)
//            val editorX = sharedPreferences3.edit()
//            editorX.clear()
//            editorX.apply()
//            val i=Intent(this,AboutUs::class.java)
//            startActivity(i)
//            finish()
//        })
//        builder.setNegativeButton(
//            "No",
//            DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
//
//        builder.show()
//    }

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