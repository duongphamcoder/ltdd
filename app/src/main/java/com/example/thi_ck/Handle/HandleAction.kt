package com.example.thi_ck.Handle

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thi_ck.Class.Chat
import com.example.thi_ck.Class.ChatItem
import com.example.thi_ck.Class.User
import com.example.thi_ck.ItemAdapter.ChatAdapter
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
        val fromReference = FirebaseFirestore.getInstance().collection("messages").document(fromID).collection(toID)
        val messageItem = ChatItem(fromID, message, toID, date)
        fromReference.add(messageItem)
            .addOnSuccessListener {
                Log.d("Chat","Save mess from success....")
            }
            .addOnFailureListener {
                Log.d("Chat","Save failed....")
            }
        val toReference = FirebaseFirestore.getInstance().collection("messages").document(toID).collection(fromID)
        toReference.add(messageItem)
            .addOnSuccessListener {
                Log.d("Chat","Save mess to success....")
            }
            .addOnFailureListener {
                Log.d("Chat","Save failed....")
            }
    }

//    fun handleProcessChatMessage(user:User):ArrayList<Chat> {
//
//        return listChat
//    }
}