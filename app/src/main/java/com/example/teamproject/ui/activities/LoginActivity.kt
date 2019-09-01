package com.example.teamproject.ui.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.teamproject.R
import com.example.teamproject.models.Admin
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    lateinit var adminAce:List<Admin>
    companion object {
        private val TAG = "LoginActivity"
        private val REQUEST_SIGNUP = 0
    }
    val aceEmail = "aceplus@gmail.com"
    val acePass = "@@ceplu$$"
    private lateinit var email: String
    private lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            login()
        }
    }

    fun login() {
        email = editTextEmail.text.toString()
        password = editTextPassword.text.toString()
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher((email)).matches()) {
            editTextEmail.error = "Enter a valid email address"
        }
        else if (password.isEmpty() || password.length < 8 || password.length > 16) {
            editTextPassword.error = "Between 8 and 16 alphanumeric characters"
        }
        else if (email == aceEmail && password == acePass) {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            editTextEmail.text!!.clear()
            editTextPassword.text!!.clear()
            finish()
        }
        else{
            Toast.makeText(this@LoginActivity,"failed",Toast.LENGTH_SHORT).show()
        }

    }
}


