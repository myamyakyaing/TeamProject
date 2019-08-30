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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.teamproject.R
import com.example.teamproject.models.Trainer
import com.example.teamproject.network.ApiService
import com.example.teamproject.network.RestAdapter
import com.example.teamproject.ui.adapters.RecyclerTeacherAdapter
import kotlinx.android.synthetic.main.activity_teacher_list.*
import kotlinx.android.synthetic.main.tech_top_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherListActivity : AppCompatActivity() {
    private val trainerListAdapter: RecyclerTeacherAdapter by lazy {
        RecyclerTeacherAdapter(
            this::onClickItem,
            this::onLongClickItem
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_teacher_list)
        setSupportActionBar(techBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rvTeacher.layoutManager = GridLayoutManager(this, 2)
        rvTeacher.adapter = trainerListAdapter
        loadPosts()
    }

    private fun onClickItem(trainer: Trainer) {
        val apiSingleCalls = RestAdapter.getClient().create(ApiService::class.java)
        val postCall = apiSingleCalls.getIndevidualTrainer(trainer.id!!)
        postCall.enqueue(object : Callback<Trainer> {

            override fun onResponse(call: Call<Trainer>, response: Response<Trainer>) {
                if (response.isSuccessful) {
                    var intent = TeacherProfileActivity.newActivity(
                        this@TeacherListActivity,
                        trainer)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@TeacherListActivity, "Response Unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Trainer>, t: Throwable) {
                Log.d("Error", "Network Error")
                Log.d("ERRRRRRRRRROOOOOOR",t.localizedMessage)
            }
        })
    }

    fun loadPosts() {
        val apiCalls = RestAdapter.getClient().create(ApiService::class.java)
        val postCall = apiCalls.getAllTrainer()
        postCall.enqueue(object : Callback<List<Trainer>> {

            override fun onResponse(call: Call<List<Trainer>>, response: Response<List<Trainer>>) {

                if (response?.body() != null) {
                    Toast.makeText(this@TeacherListActivity, "Response Successful", Toast.LENGTH_SHORT).show()
                    trainerListAdapter.setTrainerListItems(response.body()!!)
                } else {
                    Toast.makeText(this@TeacherListActivity, "Response Failed", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<List<Trainer>>, t: Throwable) {
                Log.d("Error", "Network Error")
            }
        })
    }

    private fun onLongClickItem(trainer: Trainer) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure to deleteContact?")
            .setPositiveButton("OK") { _, _ ->
                val apiSingleCalls = RestAdapter.getClient().create(ApiService::class.java)
                val postCall = apiSingleCalls.deleteIndevidualTrainer(trainer.id!!)
                postCall.enqueue(object : Callback<Trainer> {

                    override fun onResponse(call: Call<Trainer>, response: Response<Trainer>) {
                        if (response.isSuccessful) {

                            if (response?.body() != null) {
                                Toast.makeText(this@TeacherListActivity, "Response Successful", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(this@TeacherListActivity, "Response Failed", Toast.LENGTH_SHORT).show()
                            }

                        }

                    }

                    override fun onFailure(call: Call<Trainer>, t: Throwable) {
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
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menuAdd) {
            val intent = Intent(this, AddNewTrainerActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
