package com.example.teamproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.teamproject.R
import kotlinx.android.synthetic.main.activity_student_list.*
import kotlinx.android.synthetic.main.student_list_bar.*

class StudentListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_student_list)
        setSupportActionBar(student_list_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recycler_student_list.layoutManager = GridLayoutManager(this,2)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menuAdd) {
            val intent = Intent(this,AddNewStudentActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
