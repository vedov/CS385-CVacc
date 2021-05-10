package com.example.cvacc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
            finish();
        }
    }
}