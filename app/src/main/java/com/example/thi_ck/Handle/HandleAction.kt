package com.example.thi_ck.Handle

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.thi_ck.Class.ChatItem
import com.example.thi_ck.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HandleAction {

    constructor()

    fun HandleRegister(username:String, password:String, conf_password:String, email:String, conText:Context):Boolean {
        if(username.isEmpty() ||
            password.isEmpty() ||
            conf_password.isEmpty() ||
            email.isEmpty()) {
            Toast.makeText(conText, "Enter enough information..", Toast.LENGTH_LONG).show()
        }
        else if (password.length < 7) {
            Toast.makeText(conText, "Minimum password 8 characters..", Toast.LENGTH_LONG).show()
        }
        else if (!password.equals( conf_password)) {
            Toast.makeText(conText, "Incorrect password authentication..", Toast.LENGTH_LONG).show()
        }
        else {
         return true
        }
        return false
    }

    fun handleLogin(username: String, password: String, conText: Context):Boolean {
        if(username.isEmpty() ||
            password.isEmpty()
            ) {
            Toast.makeText(conText, "Enter enough information..", Toast.LENGTH_LONG).show()

            return false

        }

          return true
    }

    fun handleVerifyUserIsLogged(conText: Context) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if(uid == null) {
            val intent  = Intent(conText, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            conText.startActivity(intent)
        }
    }

    fun handleSendMessage(fromID:String, message:String, toID:String, date: Long) {
        val db = FirebaseFirestore.getInstance().collection("message")
        val messageItem = ChatItem(fromID, message, toID, date)
        db.add(messageItem)
            .addOnSuccessListener {
                Log.d("Chat", "Save success... ${db.id}")
            }
            .addOnFailureListener {
                Log.d("Chat", "Save failed... ${it.message}")
            }
    }
}