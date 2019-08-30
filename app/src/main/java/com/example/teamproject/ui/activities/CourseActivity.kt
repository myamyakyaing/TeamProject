package com.example.teamproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject.R
import com.example.teamproject.ui.adapters.RecyclerCourseAdapter
import com.example.teamproject.models.Course
import com.example.teamproject.models.CourseDetail
import com.example.teamproject.network.ApiService
import com.example.teamproject.network.RestAdapter
import com.example.teamproject.ui.adapters.CourseDetailListAdapter
import kotlinx.android.synthetic.main.activity_course.*
import kotlinx.android.synthetic.main.activity_section.*
import kotlinx.android.synthetic.main.course_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseActivity : AppCompatActivity() {
    lateinit var courseArray: List<Course>
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_course)
        setSupportActionBar(courseBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var android = Course(R.drawable.android,"Android Development",1)
        var php = Course(R.drawable.php,"Web Development",3)
        var java = Course(R.drawable.java,"Java Development",2)
        var design = Course(R.drawable.design,"Design Implementation",4)
        var basic = Course(R.drawable.basic,"Computer Basic",5)
        courseArray = mutableListOf<Course>(android,php,java,design,basic)
        var rvCourseAdapter = RecyclerCourseAdapter(courseArray,this::onClickItem)
        rvCourse.layoutManager = LinearLayoutManager(this)
        rvCourse.adapter = rvCourseAdapter
    }
    private fun onClickItem(course: Course) {
        var intent = CourseDetailActivity.newActivity(
            this@CourseActivity, course.id
        )
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
