package com.example.thi_ck.ItemAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thi_ck.Class.Chat
import com.example.thi_ck.R
import com.squareup.picasso.Picasso

class ChatAdapter(val dataset:ArrayList<Chat>):RecyclerView.Adapter<ChatAdapter.ViewHolder>() {


    // tạo khung nhìn theo item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent,false)
        return ViewHolder(view)
    }

    // lấy ra layout
    override fun getItemViewType(position: Int): Int {
        if(dataset.get(position).status == 1) {
            return R.layout.activity_chat_from
        }
        return R.layout.activity_chat_to
    }

    // set giá trị và các hoạt động khác liên quan đến các element trong layout
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.chat.text = dataset.get(position).chat_mess
        Picasso.get().load(dataset.get(position).url_image).into(holder.image)
    }


    // số lượng layout được trả về
    override fun getItemCount(): Int {
        return dataset.size
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        lateinit var image:ImageView
        lateinit var chat:TextView

        init {
            image = itemView.findViewById(R.id.image_friend)
            chat = itemView.findViewById(R.id.messs_from_friend)
        }
    }
}