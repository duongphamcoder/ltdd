package com.example.thi_ck.Class

import java.sql.Timestamp
import java.util.*

data class ChatItem(val fromID:String, val message:String, val toID:String, val date: com.google.firebase.Timestamp) {

}