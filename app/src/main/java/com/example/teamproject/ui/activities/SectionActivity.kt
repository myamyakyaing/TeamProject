package com.example.teamproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.teamproject.R
import kotlinx.android.synthetic.main.activity_section.*
import kotlinx.android.synthetic.main.section_top_bar.*

class SectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_section)

        setSupportActionBar(sectionBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linCourse.setOnClickListener {
            val intent = Intent(this,CourseActivity::class.java)
            startActivity(intent)
        }
        linTrainer.setOnClickListener {
            val intent = Intent(this,TeacherListActivity::class.java)
            startActivity(intent)
        }
        linStudent.setOnClickListener {
            val intent = Intent(this,StudentListActivity::class.java)
            startActivity(intent)
        }
        linTeam.setOnClickListener {
            val intent = Intent(this,TeamListActivity::class.java)
            startActivity(intent)
        }
        linEvaluation.setOnClickListener {
            val intent = Intent(this, EvaluationListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
