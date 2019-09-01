package com.example.teamproject.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.teamproject.models.Student
import com.example.teamproject.models.StudentList
import com.example.teamproject.network.ApiService
import com.example.teamproject.network.RestAdapter
import com.example.teamproject.ui.adapters.StudentListAdapter
import kotlinx.android.synthetic.main.activity_student_list.*
import kotlinx.android.synthetic.main.student_list_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//import android.R

class StudentListActivity : AppCompatActivity() {
    private val studentListAdapter: StudentListAdapter by lazy {
        StudentListAdapter(
            this::onClickItem,
            this::onLongClickItem
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(com.example.teamproject.R.layout.activity_student_list)
        setSupportActionBar(student_list_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recycler_student_list.layoutManager = GridLayoutManager(this, 2)
        recycler_student_list.adapter = studentListAdapter
        loadPosts()
    }

    private fun onClickItem(studentList: StudentList) {
        val apiSingleCalls = RestAdapter.getClient().create(ApiService::class.java)
        val postCall = apiSingleCalls.getIndevidualStudent(studentList.id!!)
        postCall.enqueue(object : Callback<StudentList> {

            override fun onResponse(call: Call<StudentList>, response: Response<StudentList>) {
                if (response.isSuccessful) {
                    var intent = StudentProfileActivity.newActivity(
                        this@StudentListActivity, studentList
                    )
                    startActivity(intent)
                } else {
                    Toast.makeText(this@StudentListActivity, "Response Unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<StudentList>, t: Throwable) {
                Log.d("Error", "Network Error")
            }
        })
    }

    fun loadPosts() {
        val apiCalls = RestAdapter.getClient().create(ApiService::class.java)
        val postCall = apiCalls.getAllStudent()
        postCall.enqueue(object : Callback<List<StudentList>> {

            override fun onResponse(call: Call<List<StudentList>>, response: Response<List<StudentList>>) {
                Toast.makeText(this@StudentListActivity, "Response Successful", Toast.LENGTH_SHORT).show()

                if (response?.body() != null) {
                    Toast.makeText(this@StudentListActivity, "Response Successful", Toast.LENGTH_SHORT).show()
                    studentListAdapter.setStudentListItems(response.body()!!)
                } else {
                    Toast.makeText(this@StudentListActivity, "Response Failed", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<List<StudentList>>, t: Throwable) {
                Log.d("Error", "Network Error")
                Log.d("Error", t.localizedMessage)

            }
        })
    }

    private fun onLongClickItem(studentList: StudentList) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure to deleteContact?")
            .setPositiveButton("OK") { _, _ ->
                val apiSingleCalls = RestAdapter.getClient().create(ApiService::class.java)
                val postCall = apiSingleCalls.deleteIndevidualStudent(studentList.id!!)
                postCall.enqueue(object : Callback<List<StudentList>> {
                    override fun onResponse(call: Call<List<StudentList>>, response: Response<List<StudentList>>) {
                            studentListAdapter.setStudentListItems(response.body()!!)
                    }

                    override fun onFailure(call: Call<List<StudentList>>, t: Throwable) {
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.teamproject.R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == com.example.teamproject.R.id.menuAdd) {
            val intent = Intent(this, AddNewStudentActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
