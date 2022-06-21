package com.example.thi_ck

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thi_ck.Handle.HandleAction
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var btn_login:Button
    private lateinit var handle:HandleAction
    private lateinit var redirect_register:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.username_text_login)
        password = findViewById(R.id.password_text_login)
        btn_login = findViewById(R.id.btn_login)
        redirect_register = findViewById(R.id.redirect_register)
        handle = HandleAction()
    }

    override fun onStart() {
        super.onStart()

        // xử lý đăng nhập
        btn_login.setOnClickListener {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(),password.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                      val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                        return@addOnCompleteListener
                    }
                }

                .addOnFailureListener {
                    Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                }
        }

        // xử lý chuyển qua trang đăng ký
        redirect_register.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}