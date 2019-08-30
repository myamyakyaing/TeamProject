package com.example.teamproject.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject.R
import com.example.teamproject.models.StudentList
import com.example.teamproject.models.Team
import com.example.teamproject.network.ApiService
import com.example.teamproject.network.RestAdapter
import com.example.teamproject.ui.adapters.TeamListAdapter
import kotlinx.android.synthetic.main.activity_team_list.*
import kotlinx.android.synthetic.main.team_list_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamListActivity : AppCompatActivity() {
    private val teamListAdapter: TeamListAdapter by lazy {
        TeamListAdapter(
            this::onLongClickItem
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_team_list)
        setSupportActionBar(team_list_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rv_team_list.layoutManager = LinearLayoutManager(this)
        rv_team_list.adapter = teamListAdapter
        loadPosts()
    }
    fun loadPosts() {
        val apiCalls = RestAdapter.getClient().create(ApiService::class.java)
        val postCall = apiCalls.getAllTeam()
        postCall.enqueue(object : Callback<List<Team>> {

            override fun onResponse(call: Call<List<Team>>, response: Response<List<Team>>) {
                Toast.makeText(this@TeamListActivity,"Response Successful", Toast.LENGTH_SHORT).show()
                if (response?.body() != null){
                    Toast.makeText(this@TeamListActivity,"Response Successful", Toast.LENGTH_SHORT).show()
                    teamListAdapter!!.setTeamListItems(response.body()!!)
                }

                else{
                    Toast.makeText(this@TeamListActivity,"Response Failed", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<List<Team>>, t: Throwable) {
                Log.d("Error", "Network Error")
                Log.d("ERRRRRRROOOOO",t.localizedMessage)
            }
        })
    }

    fun onLongClickItem(team: Team){
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure to deleteContact?")
            .setPositiveButton("OK") { _, _ ->
                val apiSingleCalls = RestAdapter.getClient().create(ApiService::class.java)
                val postCall = apiSingleCalls.deleteIndevidualTeam(team.id!!)
                postCall.enqueue(object : Callback<Team> {

                    override fun onResponse(call: Call<Team>, response: Response<Team>) {
                        if (response.isSuccessful) {

                            if (response?.body() != null) {
                                Toast.makeText(this@TeamListActivity, "Response Successful", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(this@TeamListActivity, "Response Failed", Toast.LENGTH_SHORT).show()
                            }

                        }

                    }

                    override fun onFailure(call: Call<Team>, t: Throwable) {
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
}
