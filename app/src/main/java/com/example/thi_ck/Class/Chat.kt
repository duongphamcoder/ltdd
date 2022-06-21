package com.example.thi_ck.Class

data class Chat(val url_image:String, val chat_mess:String,val status:Int) {

    //status : dùng để phân biệt đây là tin nhắn nhận hay là tin nhắn gửi
    // status = 0 -> tin nhắn nhận
    // status = 1 -> tin nhắn gửi
}