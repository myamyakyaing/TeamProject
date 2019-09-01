package com.example.teamproject.ui.activities

//import com.example.teamproject.ui.adapters.batchSpinnerAdapter
//import com.example.teamproject.ui.adapters.trackSpinnerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject.R
import com.example.teamproject.models.Evaluation
import com.example.teamproject.models.EvaluationData
import com.example.teamproject.network.ApiService
import com.example.teamproject.network.RestAdapter
import com.example.teamproject.ui.adapters.allSpinnerAdapter
import com.example.teamproject.ui.adapters.viewholders.studentArrayAdapter
import kotlinx.android.synthetic.main.activity_add_new_studentvaluation_e.*
import kotlinx.android.synthetic.main.add_evaluation_bar.*
import kotlinx.android.synthetic.main.recycler_evaluation_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNewStudentvaluationEActivity : AppCompatActivity() {
    private var isEdit = false
    var trackId: Int = 0
    var batchId: Int = 0
    var nameId: Int = 0
    var soft_skill = ""
    var hard_skill = ""
    var rule = ""

    companion object {
        var IE_IS_EDIT = "isEdit"
        val NAME = "name"
        val BATCH = "batch"
        val TRACK = "track"
        val SOFT = "soft"
        val HARD = "hard"
        val RULE = "rule"
        val EVALUATION_LIST = "course_list"
        lateinit var studentArray: ArrayList<String>
        fun newActivity(context: EvaluationListActivity, studentName: ArrayList<String>, isEdit: Boolean): Intent {
            val intent = Intent(context, AddNewStudentvaluationEActivity::class.java)
            intent.putExtra(EVALUATION_LIST, studentName)
            intent.putExtra(IE_IS_EDIT,isEdit)
            return intent
        }

        fun newActivity(
            context: EvaluationListActivity,
            isEdit: Boolean,
            name: String? = null,
            batch: String? = null,
            track: String? = null,
            soft: String? = null,
            hard: String? = null,
            rule: String? = null
        ): Intent {
            val intent = Intent(context, AddNewStudentvaluationEActivity::class.java)
            intent.putExtra(IE_IS_EDIT,isEdit)
            intent.putExtra(NAME, name)
            intent.putExtra(BATCH, batch)
            intent.putExtra(TRACK, track)
            intent.putExtra(SOFT, soft)
            intent.putExtra(HARD, hard)
            intent.putExtra(RULE, rule)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_new_studentvaluation_e)
        setSupportActionBar(evaluation_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        studentArray = intent.getStringArrayListExtra(EVALUATION_LIST)
        //get data from intent
        if (intent.getBooleanExtra(IE_IS_EDIT, false)) {
            isEdit = true
            txt_ev_name.text = intent.getStringExtra(NAME)
            txt_ev_track.text = intent.getStringExtra(TRACK)
            txt_ev_batch.text = intent.getStringExtra(BATCH)
            txt_ev_soft.text = intent.getStringExtra(SOFT)
            txt_ev_hard.text = intent.getStringExtra(HARD)
            txt_ev_rule.text = intent.getStringExtra(RULE)
            e_btn_save.text = "Update"
        }
        checkIsSpinnerForAll()
        e_btn_cancle.setOnClickListener {
            onBackPressed()
            true
        }
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
                        Toast.makeText(this@AddNewStudentvaluationEActivity, "Response Successful", Toast.LENGTH_SHORT)
                            .show()
                        var intent = Intent(this@AddNewStudentvaluationEActivity, EvaluationListActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@AddNewStudentvaluationEActivity, "Add Failed", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }

    }

    private fun checkIsSpinnerForAll() {
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
        var studentArrayAdapter: studentArrayAdapter = studentArrayAdapter(this, studentArray, R.layout.text_spinner)
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
                rule = skillArray[p2]
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
