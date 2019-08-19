package com.example.teamproject.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.teamproject.R
import kotlinx.android.synthetic.main.about_as_bar.*
import kotlinx.android.synthetic.main.activity_about_as.*

class AboutAsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_about_as)
        setSupportActionBar(aboutas_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        imgFB.setOnClickListener {
            val url = "https://www.facebook.com"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        imgChrome.setOnClickListener {
            val url = "https://www.google.com"
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        imgPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:09459203722")
            startActivity(intent)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
