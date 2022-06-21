package com.example.thi_ck.ItemAdapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.thi_ck.Class.User
import com.example.thi_ck.ChatWithFriendActivity
import com.example.thi_ck.R
import com.squareup.picasso.Picasso

class UserItem(val array:ArrayList<User>):RecyclerView.Adapter<UserItem.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItem.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_user_item, parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserItem.ViewHolder, position: Int) {
        holder.text.text = array.get(position).username
        Picasso.get().load(array.get(position).uri_image).into(holder.imageview)
        holder.item_layout_search.setOnClickListener {
           val intent = Intent(holder.itemView.context, ChatWithFriendActivity::class.java)
            intent.putExtra("uid",array.get(position))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return array.size
    }


    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var text:TextView
        lateinit var item_layout_search:LinearLayout
        lateinit var imageview:ImageView

        init {
            text = view.findViewById(R.id.textView)
            item_layout_search = view.findViewById(R.id.item_layout_search)
            imageview = view.findViewById(R.id.imageview)
        }
    }
}