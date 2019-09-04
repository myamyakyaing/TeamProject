package com.example.teamproject.ui.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
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
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            login()
        }
    }

    fun login() {
        Log.d(TAG, "Login")

        if (!validate()) {
            onLoginFailed()
            return
        }

        btnLogin.isEnabled = false

        val progressDialog = ProgressDialog(
            this@LoginActivity,
            R.style.AppTheme_Dark_Dialog
        )
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Login...")
        progressDialog.show()

        email = editTextEmail.text.toString()
        password = editTextPassword.text.toString()

        android.os.Handler().postDelayed(
            {
                //  On complete call either onLoginSuccess or onLoginFailed
                onLoginSuccess()
                // onLoginFailed();
                progressDialog.dismiss()
            }, 2000
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != REQUEST_SIGNUP) return
        if (resultCode == Activity.RESULT_OK) {
            // By default we just finish the Activity and log them in automatically
            this.finish()
        }
    }

    override fun onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true)
    }

    fun onLoginSuccess() {
        btnLogin.isEnabled = true
    }

    fun onLoginFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()
        btnLogin.isEnabled = true
    }

    fun validate(): Boolean {
        var valid = true

        email = editTextEmail.text.toString()
        password = editTextPassword.text.toString()
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.error = "Enter a valid email address"
            valid = false
        } else {
            editTextEmail.error = null
        }

        if (password.isEmpty() || password.length < 8 || password.length > 16) {
            editTextPassword.error = "Between 8 and 16 alphanumeric characters"
            valid = false
        } else {
            editTextPassword.error = null
        }

        if (email == aceEmail && password == acePass){
            val intent = Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(this@LoginActivity,"Login Failed",Toast.LENGTH_SHORT).show()
        }

        return valid
    }

}





