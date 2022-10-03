package com.altamash.acimsat.ui


import android.annotation.TargetApi
import android.app.Activity
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.altamash.acimsat.R
import com.altamash.acimsat.databinding.ActivityAdminResultBinding
import com.altamash.acimsat.model.ResultDetail
import com.altamash.acimsat.model.myAdapter
import com.altamash.acimsat.model.setData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

class AdminResult : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    private lateinit var collectionReference: CollectionReference
    private lateinit var binding: ActivityAdminResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_result)
        binding = ActivityAdminResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarGradiant(this)
        //Get instance
        db = FirebaseFirestore.getInstance()
        collectionReference = db.collection("Result")
        //get data from FireStore
        collectionReference.get(Source.SERVER).addOnSuccessListener { querySnapshot ->

            for (document in querySnapshot) {
                val person: ResultDetail = document.toObject(ResultDetail::class.java)
                setData.setDetail(
                    person.Name,
                    person.Phone,
                    person.City,
                    person.Email,
                    person.ObResult,
                    person.Question1,
                    person.Question2,
                    person.Question3,
                    person.Question4,
                    person.Question5,
                )
            }

        }
        //Set on Rescycler View after a sec
        Handler(Looper.getMainLooper()).postDelayed({
            //Do something after 100ms
            binding.mrRecycler.adapter = myAdapter(setData.getAll())
            binding.mrRecycler.layoutManager = LinearLayoutManager(this)
        }, 1000)

        binding.DeleteAll.setOnClickListener {
            deleteResult()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setData.deleteAll()
        finish()
    }

    private fun deleteResult() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All Result Data?.")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            db = FirebaseFirestore.getInstance()
            db.collection("Result").document().delete()
        })
        builder.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

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