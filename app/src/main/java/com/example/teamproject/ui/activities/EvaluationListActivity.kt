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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject.R
import com.example.teamproject.models.Evaluation
import com.example.teamproject.models.StuswntNameList
import com.example.teamproject.network.ApiService
import com.example.teamproject.network.RestAdapter
import com.example.teamproject.ui.adapters.EvaluationListAdapter
import kotlinx.android.synthetic.main.activity_evaluation_list.*
import kotlinx.android.synthetic.main.evaluation_list_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EvaluationListActivity : AppCompatActivity() {
    val evList:List<Evaluation> = emptyList()
    var myArray :Array<String> = emptyArray()
    var list:String = ""
    var  evaluationListAdapter: EvaluationListAdapter = EvaluationListAdapter()
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
        for (i in 0..evList.size - 1) {
            list = evList[i].student.name
        }
        myArray = arrayOf(list)
        Log.d("Student Array","$myArray")
    }
    fun loadPosts() {
        val apiCalls = RestAdapter.getClient().create(ApiService::class.java)
        val postCall = apiCalls.getAllEvaluation()
        postCall.enqueue(object : Callback<List<Evaluation>> {
            override fun onResponse(call: Call<List<Evaluation>>, response: Response<List<Evaluation>>) {

                if (response?.body() != null){
                    Toast.makeText(this@EvaluationListActivity,"Response Successful", Toast.LENGTH_SHORT).show()
                    evaluationListAdapter!!.setTeamListItems(response.body()!!)
                }

                else{
                    Toast.makeText(this@EvaluationListActivity,"Response Failed", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<List<Evaluation>>, t: Throwable) {
                Log.d("Error", "Network Error")
                Log.d("EEEERRRRRROOOORRRRRRRR",t.localizedMessage)

            }
        })
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
            val intent = AddNewStudentvaluationEActivity.newActivity(this,myArray)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
