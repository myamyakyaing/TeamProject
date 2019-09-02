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
import com.example.teamproject.models.CourseData
import com.example.teamproject.models.CourseDetail
import com.example.teamproject.models.CourseDetailData
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
    val courseDetail:List<CourseDetail>? = null
    var id :Int = 0
    private val courseDetailListAdapter: CourseDetailListAdapter by lazy { CourseDetailListAdapter(this::onClickItem, this::onLongClickItem) }
    companion object {
        var course_detail_list = emptyList<CourseDetail>()
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

//    override fun onResume() {
//        super.onResume()
//        courseDetailListAdapter.setCourseDetailListItems(course_detail_list)
//    }
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

    fun onClickItem(courseDetail:CourseDetail){
        Log.d("EEEEERFGBJJ","${courseDetail.courseName}")
        var intent = AddCourseDetailActivity.newActivity(
            this@CourseDetailActivity, true, courseDetail
        )
        startActivity(intent)

    }
    fun onLongClickItem(courseDetail:CourseDetail){
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure to deleteContact?")
            .setPositiveButton("OK") { _, _ ->
                val apiSingleCalls = RestAdapter.getClient().create(ApiService::class.java)
                val postCall = apiSingleCalls.deleteIndevidualCourse(courseDetail.id!!)
                postCall.enqueue(object : Callback<List<CourseDetail>> {

                    override fun onResponse(call: Call<List<CourseDetail>>, response: Response<List<CourseDetail>>) {
                        if (response.isSuccessful) {
                            if (response?.body() != null) {
                                course_detail_list = response.body()!!
                                courseDetailListAdapter.setCourseDetailListItems(response.body()!!)

                            } else {
                                Toast.makeText(this@CourseDetailActivity, "Response Failed", Toast.LENGTH_SHORT).show()
                            }

                        }

                    }

                    override fun onFailure(call: Call<List<CourseDetail>>, t: Throwable) {
                        Log.d("Error", "Network Error")
                    }
                })
                Toast.makeText(applicationContext, "Successfully deleted.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { _, _ ->

            }
            .create()
        alertDialog.show()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menuAdd) {
            var intent = AddCourseDetailActivity.newActivity(
                this@CourseDetailActivity, id,false
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
