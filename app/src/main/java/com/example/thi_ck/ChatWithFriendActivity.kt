package com.example.thi_ck

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thi_ck.Class.Chat
import com.example.thi_ck.Class.User
import com.example.thi_ck.Handle.HandleAction
import com.example.thi_ck.ItemAdapter.ChatAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class ChatWithFriendActivity:AppCompatActivity() {
    private lateinit var user:User
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : RecyclerView.Adapter<ChatAdapter.ViewHolder>
    private lateinit var btn_send_mess:Button
    private lateinit var edittext_mess:EditText
    private lateinit var handleAction: HandleAction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        user = intent.getParcelableExtra<User>("uid")!!
        supportActionBar?.title = "${user.username}"
        btn_send_mess = findViewById(R.id.btn_send_mess)
        edittext_mess = findViewById(R.id.edittext_mess)
        recyclerView = findViewById(R.id.recyclerview_chat)
        handleAction = HandleAction()
    }

    override fun onStart() {
        super.onStart()

        // lắng nghe sự thay đổi của các document trong collection users
        val db = FirebaseFirestore.getInstance().collection("message")
        db.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("Chat", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val dataset:ArrayList<Chat> = ArrayList()
//                snapshot.documents.sortBy { it["date"] as Date }
                var listData = snapshot.documents
                listData.sortBy { it["date"] as Long }
                for(item in listData) {
                   if (item["fromID"].toString().equals(FirebaseAuth.getInstance()?.uid.toString())) {
                       dataset.add(Chat(user.uri_image,item["message"].toString(),0))
                   }
                    else {
                       dataset.add(Chat(user.uri_image,item["message"].toString(),1))
                   }
                }

                adapter = ChatAdapter(dataset)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter
            } else {
                Log.d("Chat", "Current data: null")
            }
        }


        btn_send_mess.setOnClickListener {
            handleAction.handleSendMessage(FirebaseAuth.getInstance()?.uid.toString(),edittext_mess.text.toString(), user.uid,
                Date().time
            )
            edittext_mess.setText("")
        }



    }
}