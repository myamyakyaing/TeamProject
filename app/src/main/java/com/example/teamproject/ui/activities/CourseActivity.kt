package com.example.teamproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject.R
import com.example.teamproject.ui.adapters.RecyclerCourseAdapter
import com.example.teamproject.models.Course
import kotlinx.android.synthetic.main.activity_course.*
import kotlinx.android.synthetic.main.course_bar.*

class CourseActivity : AppCompatActivity() {
    lateinit var courseArray: List<Course>

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_course)
        setSupportActionBar(courseBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var android = Course(R.drawable.android,"Android Development")
        var php = Course(R.drawable.php,"Web Development")
        var java = Course(R.drawable.java,"Java Development")
        var design = Course(R.drawable.design,"Design Implementation")
        var basic = Course(R.drawable.basic,"Computer Basic")
        courseArray = mutableListOf<Course>(android,php,java,design,basic)
        var rvCourseAdapter = RecyclerCourseAdapter(courseArray, this::onClickItem)
        rvCourse.layoutManager = LinearLayoutManager(this)
        rvCourse.adapter = rvCourseAdapter
    }
    private fun onClickItem(course:Course){
        val intent = Intent(this, CourseDetailActivity::class.java)
        startActivity(intent)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
