package com.example.thi_ck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.thi_ck.Class.User
import com.example.thi_ck.Handle.HandleAction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var username:EditText
    private lateinit var password:EditText
    private lateinit var conf_password:EditText
    private lateinit var email:EditText
    private lateinit var btn_register:Button
    private lateinit var redirect_login:TextView
    private lateinit var handle:HandleAction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        handle = HandleAction()

        username = findViewById(R.id.username_text_register)
        password = findViewById(R.id.password_text_register)
        conf_password = findViewById(R.id.conf_password_text_register)
        email = findViewById(R.id.email_text_register)
        btn_register = findViewById(R.id.btn_register)
        redirect_login = findViewById(R.id.redirect_login)


    }

    override fun onStart() {
        super.onStart()

        btn_register.setOnClickListener {
           val check = handle.HandleRegister(username.text.toString(), password.text.toString(), conf_password.text.toString(), email.text.toString(), this)
            if(check) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if (!it.isSuccessful) {
                            Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
                            return@addOnCompleteListener
                        }

                        val db = FirebaseFirestore.getInstance()
                        val uid = it.getResult().user?.uid.toString()
                        val name = username.text.toString()
                        val image_url = "https://media.istockphoto.com/vectors/default-avatar-man-to-social-user-vector-id860264876?k=6&m=860264876&s=170667a&w=0&h=2YApZVjoA2w1PBJonLA-PUfgbIHltDXqBZWlJI2Zo7M="
                        val user = User(uid, name, image_url)

                        db.collection("users").document(uid)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d("Register","Save success")
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener {
                                Log.d("Register","Save failed")
                            }

                    }
                    .addOnFailureListener {
                        Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
                    }

            }
            else return@setOnClickListener

    }




        redirect_login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}