package com.example.thi_ck

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArraySet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thi_ck.Class.HomeItem
import com.example.thi_ck.Handle.HandleAction
import com.example.thi_ck.ItemAdapter.HomeAdapter
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {
    private lateinit var uid:String
    private lateinit var handle:HandleAction
    private lateinit var intent:Any
    private lateinit var adapter: RecyclerView.Adapter<HomeAdapter.ViewHolder>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.title = "Messenger"
        handle = HandleAction()
        handle.handleVerifyUserIsLogged(this)
        uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        recyclerView = findViewById(R.id.home_recyclerview)

//        val dataset:ArrayList<HomeItem> = ArrayList()
//        dataset.add(HomeItem("WaIdUPPfXpTbuo8zxWWeuyRhl4U2","Duong ne","test home"))
//        dataset.add(HomeItem("THIZMaV0y6OOWvrvBtxba6yUwnL2","Anh","test home"))
//        dataset.add(HomeItem("xcp61oYQVggdd9hEdKj9TZ4z8Kx2","Nhi","test home"))
//        dataset.add(HomeItem("yuUhat923MRSpw8e8aQ5Axr2IJi1","Van","test home"))



    }

    override fun onStart() {
        super.onStart()
        val dataset:ArrayList<HomeItem> = ArrayList()
        val db = FirebaseFirestore.getInstance().collection("users")
            db.get()
                .addOnSuccessListener {
                    for(i in it.documents) {
                      if(!i["uid"].toString().equals(FirebaseAuth.getInstance()?.uid.toString())) {
                          val qr = FirebaseFirestore.getInstance().collection("messages")
                              .document(FirebaseAuth.getInstance()?.uid.toString()).collection(i["uid"].toString()).orderBy("date",Query.Direction.DESCENDING)
                              .addSnapshotListener { snap, e ->
                                  val data =snap?.documents
                                    data?.sortByDescending{
                                        val timestamp = it.get("date") as Timestamp
                                        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                                        val sdf = SimpleDateFormat("MM/dd/yyyy")
                                        val netDate = Date(milliseconds)
                                        val date = sdf.format(netDate)
                                        netDate.time
                                    }
                                  if(data?.size != 0) {
                                      dataset.add(HomeItem(i["uid"].toString(),i["username"].toString(),data?.get(0)?.get("message").toString()))
                                      adapter = HomeAdapter(dataset)
                                      recyclerView.layoutManager = LinearLayoutManager(this)
                                      recyclerView.adapter = adapter
                                  }

                              }
                      }
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