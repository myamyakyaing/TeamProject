package com.example.teamproject.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject.R
import com.example.teamproject.models.Evaluation
import com.example.teamproject.models.StudentList
import com.example.teamproject.network.ApiService
import com.example.teamproject.network.RestAdapter
import com.example.teamproject.ui.adapters.EvaluationListAdapter
import kotlinx.android.synthetic.main.activity_evaluation_list.*
import kotlinx.android.synthetic.main.evaluation_list_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EvaluationListActivity : AppCompatActivity() {

    companion object {
        var evaluation_list = emptyList<Evaluation>()
    }
    private val evaluationListAdapter: EvaluationListAdapter by lazy {
        EvaluationListAdapter(
            this::onClickItem,
            this::onLongClickItem
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_evaluation_list)
        setSupportActionBar(evaluation_list_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rv_evaluation_list.layoutManager = LinearLayoutManager(this)
        rv_evaluation_list.adapter = evaluationListAdapter
        loadPosts()
    }
//    override fun onResume() {
//        super.onResume()
//        evaluationListAdapter.setTeamListItems(evaluation_list)
//    }

    fun loadPosts() {
        val apiCalls = RestAdapter.getClient().create(ApiService::class.java)
        val postCall = apiCalls.getAllEvaluation()
        postCall.enqueue(object : Callback<List<Evaluation>> {
            override fun onResponse(call: Call<List<Evaluation>>, response: Response<List<Evaluation>>) {
                if (response?.body() != null) {
                    evaluationListAdapter!!.setTeamListItems(response.body()!!)
                } else {
                    Toast.makeText(this@EvaluationListActivity, "Response Failed", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<List<Evaluation>>, t: Throwable) {
                Log.d("Error", "Network Error")
                Log.d("EEEERRRRRROOOORRRRRRRR", t.localizedMessage)

            }
        })
    }
    fun onClickItem(evaluation: Evaluation) {
        Toast.makeText(this@EvaluationListActivity,"Update Evaluation List",Toast.LENGTH_SHORT).show()
    }

    fun onLongClickItem(evaluation: Evaluation) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure to deleteContact?")
            .setPositiveButton("OK") { _, _ ->
                val apiSingleCalls = RestAdapter.getClient().create(ApiService::class.java)
                val postCall = apiSingleCalls.deleteIndevidualEvaluation(evaluation.id!!)
                postCall.enqueue(object : Callback<List<Evaluation>> {
                    override fun onResponse(call: Call<List<Evaluation>>, response: Response<List<Evaluation>>) {
                        if (response.isSuccessful) {
                            if (response?.body() != null) {
                                evaluation_list = response.body()!!
                                evaluationListAdapter!!.setTeamListItems(response.body()!!)
                            } else {
                                Toast.makeText(this@EvaluationListActivity, "Response Failed", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }

                    }

                    override fun onFailure(call: Call<List<Evaluation>>, t: Throwable) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menuAdd) {
            val intent = Intent(this@EvaluationListActivity,AddNewStudentvaluationEActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
