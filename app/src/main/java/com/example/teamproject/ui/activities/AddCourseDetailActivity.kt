package com.example.teamproject.ui.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject.R
import com.example.teamproject.models.CourseData
import com.example.teamproject.models.CourseDetail
import com.example.teamproject.models.CourseDetailData
import com.example.teamproject.network.ApiService
import com.example.teamproject.network.RestAdapter
import com.example.teamproject.ui.adapters.allSpinnerAdapter
import kotlinx.android.synthetic.main.activity_add_course_detail.*
import kotlinx.android.synthetic.main.add_course_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AddCourseDetailActivity : AppCompatActivity() {
    private var isEdit = false
    private var courseDetailData: CourseDetailData? = null
    var batchId: Int = 0
    var id = 0
    lateinit var s_date: CharSequence
    lateinit var e_date: CharSequence
    var start_date = ""
    var end_date = ""
    var button_date: ImageButton? = null
    var button_date1: ImageButton? = null
    var cal = Calendar.getInstance()

    companion object {
        var IE_IS_EDIT = "isEdit"
        val COURSE_ID = "day"
        val COURSE_LIST = "course_list"
        fun newActivity(
            context: CourseDetailActivity, id: Int, isEdit: Boolean
        ): Intent {
            val intent = Intent(context, AddCourseDetailActivity::class.java)
            intent.putExtra(COURSE_LIST, id)
            intent.putExtra(IE_IS_EDIT, isEdit)
            return intent

        }

        fun newActivity(
            context: CourseDetailActivity,
            isEdit: Boolean,
            courseDetail: CourseDetail
        ): Intent {
            val intent = Intent(context, AddCourseDetailActivity::class.java)
            intent.putExtra(IE_IS_EDIT, isEdit)
            intent.putExtra("COURSE_DETAIL", courseDetail)
            return intent

        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_course_detail)
        setSupportActionBar(add_course_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //get data from intent
        if (intent.getBooleanExtra(IE_IS_EDIT, false)) {
            isEdit = true
            val course = intent.extras!!.getSerializable("COURSE_DETAIL") as CourseDetail
            val courseId = course.id
            val courseName = course.courseName
            text_view_date.text = (course.startDate).toString()
            text_view_date_3.setText(course.endDate)
            if (course.batch!!.id == 1) {
                c_batch_spinner.setSelection(0)
            } else if (course.batch!!.id == 2) {
                c_batch_spinner.setSelection(1)
            } else {
                c_batch_spinner.setSelection(2)
            }
            val day = course.teachingDay
            editTextName.setText(courseName)
            edt_teaching_day.setText(day)
            course_btn_save.text = "Update"
        }
        //newActivity
        id = intent.getIntExtra(COURSE_LIST, 0)

        checkISDatePickerForStartDate()
        checkISDatePickerForEndDate()
        checkIsSpinnerForTrackAndBatch()
        course_btn_cancle.setOnClickListener {
            onBackPressed()
            true
        }
        course_btn_save.setOnClickListener {
            if (editTextName.text.isEmpty()) {
                editTextName.error = "Enter Course Title"
            }
            if (edt_teaching_day.text.isEmpty()) {
                edt_teaching_day.error = "Enter Teaching Day"
            }
            start_date = s_date.toString()
            end_date = e_date.toString()
            val apiCalls = RestAdapter.getClient().create(ApiService::class.java)
            val loginCall = apiCalls.sendCourseData(
                CourseData(
                    editTextName.text.toString(),
                    start_date,
                    end_date,
                    id,
                    batchId,
                    edt_teaching_day.text.toString()

                )
            )
            loginCall.enqueue(object : Callback<CourseDetail> {
                override fun onFailure(call: Call<CourseDetail>, t: Throwable) {
                    Log.d("Add Course Eror:", t.localizedMessage)
                }

                override fun onResponse(call: Call<CourseDetail>, response: Response<CourseDetail>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddCourseDetailActivity, "Response Successful", Toast.LENGTH_SHORT)
                            .show()
                        var intent = Intent(this@AddCourseDetailActivity, CourseDetailActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@AddCourseDetailActivity, "Add Failed", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }

    }

    private fun checkISDatePickerForStartDate() {
        // get the references from layout file
        button_date = this.buttonDate
        text_view_date!!.text = "Start Date"
        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateStartDateInView()
            }
        }
        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        lin_start_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@AddCourseDetailActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })

    }

    private fun checkISDatePickerForEndDate() {
        // get the references from layout file
        button_date1 = this.buttonDate1
        text_view_date_3!!.text = "End Date"
        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateEndDateInView()
            }
        }
        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        lin_end_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@AddCourseDetailActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })

    }

    private fun updateStartDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        text_view_date!!.text = sdf.format(cal.getTime())
        s_date = text_view_date!!.text
        s_date = sdf.format(cal.getTime())
    }

    private fun updateEndDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        text_view_date_3!!.text = sdf.format(cal.getTime())
        e_date = text_view_date_3!!.text
        e_date = sdf.format(cal.getTime())
    }

    private fun checkIsSpinnerForTrackAndBatch() {
        //bath spinner
        var batchArray = resources.getStringArray(R.array.batch_no)
        var bathArrayAdapter: allSpinnerAdapter = allSpinnerAdapter(this, batchArray, R.layout.text_spinner)
        c_batch_spinner.adapter = bathArrayAdapter

        c_batch_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddCourseDetailActivity, "Ok", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                batchId = p2 + 1
            }

        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
