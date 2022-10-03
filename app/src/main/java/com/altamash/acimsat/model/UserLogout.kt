package com.altamash.acimsat.model

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth

class UserLogout {
    fun logout() {
        //Main Logout from Firebase
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        auth.signOut()
    }
}