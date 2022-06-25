package com.example.thi_ck.ItemAdapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.collection.ArraySet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.thi_ck.ChatWithFriendActivity
import com.example.thi_ck.Class.HomeItem
import com.example.thi_ck.Class.User
import com.example.thi_ck.R
import com.squareup.picasso.Picasso

class HomeAdapter(val dataset: ArrayList<HomeItem>):RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        lateinit var username:TextView
        lateinit var messages:TextView
        lateinit var image:ImageView
        lateinit var friend_uid:ConstraintLayout
        lateinit var no_friend_chat:TextView

        init {
           if (dataset.size != 0) {
               username = view.findViewById(R.id.username_friend)
               messages = view.findViewById(R.id.mess_chat)
               image = view.findViewById(R.id.image_friend)
               friend_uid = view.findViewById(R.id.friend_uid)
           }
            else
               no_friend_chat = view.findViewById(R.id.no_friend_chat)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(dataset.size != 0) {
            val image_url = "https://media.istockphoto.com/vectors/default-avatar-man-to-social-user-vector-id860264876?k=6&m=860264876&s=170667a&w=0&h=2YApZVjoA2w1PBJonLA-PUfgbIHltDXqBZWlJI2Zo7M="

            holder.username.setText(dataset.get(position).username)
            holder.messages.setText(dataset.get(position).messages)
            Picasso.get().load(image_url).into(holder.image)

            holder.friend_uid.setOnClickListener {
                val intent = Intent(holder.itemView.context,ChatWithFriendActivity::class.java)
                val user = User(dataset.get(position).uid,dataset.get(position).username,image_url)
                intent.putExtra("uid",user)
                holder.itemView.context.startActivity(intent)
            }
        }
        else {
            holder.no_friend_chat.setText("Trống")
        }


    }

    override fun getItemViewType(position: Int): Int {
        if(dataset.size == 0) {
            return R.layout.activity_no_friend_chat
        }
        return R.layout.activity_home_item
    }

    override fun getItemCount(): Int {
       return dataset.size
    }
}