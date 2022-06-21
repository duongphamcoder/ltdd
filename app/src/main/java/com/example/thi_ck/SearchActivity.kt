package com.example.thi_ck

import android.R.layout.simple_list_item_1
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thi_ck.ItemAdapter.UserItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.squareup.picasso.Picasso

class SearchActivity:AppCompatActivity() {
    private lateinit var search_btn:Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var search_input:EditText
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter:RecyclerView.Adapter<UserItem.ViewHolder>
    private lateinit var arr:ArrayList<com.example.thi_ck.Class.User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.title = "Search"

        recyclerView = findViewById(R.id.recycler_search)
        search_btn = findViewById(R.id.search_btn)
        search_input = findViewById(R.id.search_input)
        layoutManager = LinearLayoutManager(this)
        arr = ArrayList()
    }

    override fun onStart() {
        super.onStart()
        search_btn.setOnClickListener {
            if(search_input.text.isEmpty()) {
                Toast.makeText(this, "Don't leave it blank.",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val db = FirebaseFirestore.getInstance()


            val capitalCities = db.collection("users")
                .whereEqualTo("username", search_input.text.toString())
                capitalCities.get()
                    .addOnSuccessListener {
                        for (document in it.documents) {
                           if(!document["uid"].toString().equals(FirebaseAuth.getInstance().currentUser?.uid.toString())) {
                               val user = com.example.thi_ck.Class.User(document.get("uid").toString(),document.get("username").toString(),document.get("uri_image").toString())
                               arr.add(user)
                           }
                        }
                        adapter = UserItem(arr)
                        recyclerView.layoutManager = layoutManager
                        recyclerView.adapter = adapter
                    }
                    .addOnFailureListener {
                        Log.d("Search", "Not found.")
                    }

        }
    }
}