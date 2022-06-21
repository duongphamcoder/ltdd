package com.example.thi_ck

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thi_ck.Handle.HandleAction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity() {
    private lateinit var uid:String
    private lateinit var  image_user:ImageView
    private val db = FirebaseFirestore.getInstance().collection("users")
    private lateinit var handle:HandleAction

    private lateinit var intent:Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.title = "Messenger"
        image_user=findViewById(R.id.image_user)
        handle = HandleAction()
        handle.handleVerifyUserIsLogged(this)
        uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    }

    override fun onStart() {
        super.onStart()

        val btn = findViewById<Button>(R.id.btn_logout)

        btn.setOnClickListener {
           val db = FirebaseFirestore.getInstance().collection("friends")
            db.document(FirebaseAuth.getInstance().uid.toString())
                .set(hashMapOf("list_friends" to listOf<String>("duong","quy","phuoc")))
                .addOnSuccessListener {
                    Log.d("Home","Success..")
                }
                .addOnFailureListener {
                    Log.d("Home","Failed...")
                }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.signOut_btn -> {
                FirebaseAuth.getInstance().signOut()
                intent = Intent(this, LoginActivity::class.java)
                (intent as Intent).flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent as Intent)
            }
            R.id.search_btn -> {
                intent = Intent(this, SearchActivity::class.java)
                startActivity(intent as Intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}