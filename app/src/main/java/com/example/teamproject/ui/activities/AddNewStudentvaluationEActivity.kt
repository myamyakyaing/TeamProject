package com.example.teamproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Toast
import com.example.teamproject.R
import com.example.teamproject.models.Evaluation
import com.example.teamproject.models.EvaluationData
import com.example.teamproject.models.Trainer
import com.example.teamproject.network.ApiService
import com.example.teamproject.network.RestAdapter
import com.example.teamproject.ui.adapters.allSpinnerAdapter
//import com.example.teamproject.ui.adapters.batchSpinnerAdapter
//import com.example.teamproject.ui.adapters.trackSpinnerAdapter
import kotlinx.android.synthetic.main.activity_add_new_studentvaluation_e.*
import kotlinx.android.synthetic.main.add_evaluation_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNewStudentvaluationEActivity : AppCompatActivity() {
    var trackId: Int = 0
    var batchId: Int = 0
    var nameId :Int = 0
    var soft_skill = ""
    var hard_skill = ""
    var rule = ""
    companion object {
        val EVALUATION_LIST = "course_list"
        lateinit var studentArray:Array<String>
        fun newActivity(context: EvaluationListActivity, studentName:Array<String>): Intent {
            val intent = Intent(context, AddNewStudentvaluationEActivity::class.java)
            intent.putExtra(EVALUATION_LIST, studentName)
            return intent

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_new_studentvaluation_e)
        setSupportActionBar(evaluation_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        studentArray = intent.getStringArrayExtra(EVALUATION_LIST)
        checkIsSpinnerForAll()

        e_btn_save.setOnClickListener {
            val apiCalls = RestAdapter.getClient().create(ApiService::class.java)
            val loginCall = apiCalls.sendEvaluationData(
                EvaluationData(
                    batchId,
                    hard_skill,
                    rule,
                    soft_skill,
                    nameId,
                    trackId

                )
            )
            loginCall.enqueue(object : Callback<Evaluation> {
                override fun onFailure(call: Call<Evaluation>, t: Throwable) {
                    Toast.makeText(this@AddNewStudentvaluationEActivity, "Response Failed", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Evaluation>, response: Response<Evaluation>) {
                    //Toast.makeText(this@AddNewStudentActivity, "Response Successful", Toast.LENGTH_SHORT).show()
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddNewStudentvaluationEActivity, "Response Successful", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this@AddNewStudentvaluationEActivity, EvaluationListActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@AddNewStudentvaluationEActivity, "Add Failed", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }

    }

    private fun checkIsSpinnerForAll(){
        //track spinner
        var trackArray = resources.getStringArray(R.array.track_name)
        var trackArrayAdapter: allSpinnerAdapter = allSpinnerAdapter(this, trackArray, R.layout.text_spinner)
        e_track_spinner.adapter = trackArrayAdapter

        e_track_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddNewStudentvaluationEActivity, "Ok", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               trackId = p2 + 1

            }

        }
        //student array spinner
        var studentArrayAdapter: allSpinnerAdapter = allSpinnerAdapter(this, studentArray, R.layout.text_spinner)
        e_std_spinner.adapter = studentArrayAdapter

        e_std_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddNewStudentvaluationEActivity, "Ok", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                nameId = p2 + 1
            }

        }
        //bath spinner
        var batchArray = resources.getStringArray(R.array.batch_no)
        var bathArrayAdapter: allSpinnerAdapter = allSpinnerAdapter(this, batchArray, R.layout.text_spinner)
        e_batch_spinner.adapter = bathArrayAdapter

        e_batch_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddNewStudentvaluationEActivity, "Ok", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                batchId = p2 + 1
            }

        }
        var skillArray = resources.getStringArray(R.array.skill)
        //bath spinner
        var softArrayAdapter: allSpinnerAdapter = allSpinnerAdapter(this, skillArray, R.layout.text_spinner)
        e_soft_spinner.adapter = softArrayAdapter

        e_soft_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddNewStudentvaluationEActivity, "Ok", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@AddNewStudentvaluationEActivity, skillArray[p2], Toast.LENGTH_SHORT).show()
                soft_skill = skillArray[p2]
            }

        }
        //hard skill spinner
        var hardArrayAdapter: allSpinnerAdapter = allSpinnerAdapter(this, skillArray, R.layout.text_spinner)
        e_hard_spinner.adapter = hardArrayAdapter

        e_hard_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddNewStudentvaluationEActivity, "Ok", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@AddNewStudentvaluationEActivity, skillArray[p2], Toast.LENGTH_SHORT).show()
                hard_skill = skillArray[p2]

            }

        }
        //following rule skill spinner
        var ruleArrayAdapter: allSpinnerAdapter = allSpinnerAdapter(this, skillArray, R.layout.text_spinner)
        e_rile_spinner.adapter = ruleArrayAdapter

        e_rile_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddNewStudentvaluationEActivity, "Ok", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@AddNewStudentvaluationEActivity, skillArray[p2], Toast.LENGTH_SHORT).show()
                rule = skillArray[p2]
            }

        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
