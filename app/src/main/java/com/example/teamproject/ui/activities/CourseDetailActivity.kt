package com.example.teamproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject.R
import com.example.teamproject.models.Course
import com.example.teamproject.models.CourseDetail
import com.example.teamproject.network.ApiService
import com.example.teamproject.network.RestAdapter
import com.example.teamproject.ui.adapters.CourseDetailListAdapter
import kotlinx.android.synthetic.main.activity_add_course_detail.*
import kotlinx.android.synthetic.main.activity_course_detail.*
import kotlinx.android.synthetic.main.course_detail_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseDetailActivity : AppCompatActivity() {
    var id :Int = 0
    val courseDetailListAdapter: CourseDetailListAdapter = CourseDetailListAdapter()
    companion object {
        var trackId:Int = 0
        val COURSE_LIST = "course_list"
        fun newActivity(
            context: CourseActivity,id:Int): Intent {
            val intent = Intent(context, CourseDetailActivity::class.java)
            intent.putExtra(COURSE_LIST,id)
            return intent

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_course_detail)
        setSupportActionBar(course_detail_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        id = intent.getIntExtra(COURSE_LIST,0)

        recycler_course_detail.layoutManager = LinearLayoutManager(this)
        recycler_course_detail.adapter = courseDetailListAdapter
        trackId = intent.getIntExtra(COURSE_LIST,0)
        loadCourses()
    }

    fun loadCourses() {
        val apiCalls = RestAdapter.getClient().create(ApiService::class.java)
        val postCall = apiCalls.getAllCourseDetail()
        postCall.enqueue(object : Callback<List<CourseDetail>> {

            override fun onResponse(call: Call<List<CourseDetail>>, response: Response<List<CourseDetail>>) {
                if (response?.body() != null) {
                    Toast.makeText(this@CourseDetailActivity, "Response Successful", Toast.LENGTH_SHORT).show()
                    courseDetailListAdapter.setCourseDetailListItems(response.body()!!)
                } else {
                    Toast.makeText(this@CourseDetailActivity, "Response Failed", Toast.LENGTH_SHORT).show()
                }

            }
            override fun onFailure(call: Call<List<CourseDetail>>, t: Throwable) {
                Log.d("Error", "Network Error")
                Log.d("Errordfgfccgcghgcgggcgh", t.localizedMessage)

            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menuAdd) {
            var intent = AddCourseDetailActivity.newActivity(
                this@CourseDetailActivity, id
            )
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
