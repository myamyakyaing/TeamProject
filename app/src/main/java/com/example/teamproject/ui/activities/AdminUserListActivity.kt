package com.example.teamproject.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject.R
import com.example.teamproject.models.Admin
import com.example.teamproject.models.Trainer
import com.example.teamproject.network.ApiService
import com.example.teamproject.network.RestAdapter
import com.example.teamproject.ui.adapters.RecyclerAdminAdapter
import kotlinx.android.synthetic.main.activity_admin_user_list.*
import kotlinx.android.synthetic.main.admin_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminUserListActivity : AppCompatActivity() {
    private val adminListAdapter: RecyclerAdminAdapter = RecyclerAdminAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_admin_user_list)
        setSupportActionBar(admin_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rv_admin_list.layoutManager = LinearLayoutManager(this)
        rv_admin_list.adapter = adminListAdapter
        loadPosts()
    }
    fun loadPosts() {
        val apiCalls = RestAdapter.getClient().create(ApiService::class.java)
        val postCall = apiCalls.getAllTrainer()
        postCall.enqueue(object : Callback<List<Trainer>> {

            override fun onResponse(call: Call<List<Trainer>>, response: Response<List<Trainer>>) {

                if (response?.body() != null){
                    Toast.makeText(this@AdminUserListActivity,"Response Successful", Toast.LENGTH_SHORT).show()
                    adminListAdapter!!.setAdminListItems(response.body()!!)
                }

                else{
                    Toast.makeText(this@AdminUserListActivity,"Response Failed", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<List<Trainer>>, t: Throwable) {
                Log.d("Error", "Network Error")
            }
        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
