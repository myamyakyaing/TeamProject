package com.example.teamproject.ui.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.teamproject.R
import com.example.teamproject.models.Student
import com.example.teamproject.models.StudentData
import com.example.teamproject.models.StudentList
import com.example.teamproject.network.ApiService
import com.example.teamproject.network.RestAdapter
import com.example.teamproject.ui.adapters.allSpinnerAdapter
//import com.example.teamproject.ui.adapters.batchSpinnerAdapter
//import com.example.teamproject.ui.adapters.trackSpinnerAdapter
import kotlinx.android.synthetic.main.activity_add_new_student.*
import kotlinx.android.synthetic.main.activity_add_new_student.view.*
import kotlinx.android.synthetic.main.activity_add_new_trainer.*
import kotlinx.android.synthetic.main.new_student_bar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Files.readAllBytes
import java.nio.file.Path
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*

class AddNewStudentActivity : AppCompatActivity() {
    val REQUEST_IMAGE_GET = 1
    var button_date: ImageButton? = null
    lateinit var dob:CharSequence
    var date = ""
    var cal = Calendar.getInstance()
    var trackId: Int = 0
    var batchId: Int = 0
    var gender: String = ""
    var image:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_new_student)
        setSupportActionBar(add_student_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        checkISDatePickerForDob()
        checkIsSpinnerForTrackAndBatch()
        checkUploadImage()

        btn_student_save.setOnClickListener {
            checkEditTextIsEmpty()
            radio_button_click()
            date = dob.toString()
            addNewStudent()
            clearEditText()

        }
    }
    private fun checkUploadImage(){
        add_imgStudent.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_GET)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            val fullPhotoUri: Uri = data!!.data!!
            Glide.with(this).load(fullPhotoUri).into(add_imgStudent)
            val bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fullPhotoUri)
            add_imgStudent.setImageBitmap(bitmap)
            val imgStudent = add_imgStudent.getDrawable()
            var encodedImageData = getEncoded64ImageStringFromBitmap(bitmap)
        }
    }
    fun getEncoded64ImageStringFromBitmap(bitmap: Bitmap): String {
        val stream: ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream!!)
        val byteFormat = stream.toByteArray()
        image = Base64.encodeToString(byteFormat, Base64.NO_WRAP)
        return image
    }
    fun radio_button_click() {
        // Get the checked radio button id from radio group
        var id: Int = student_group.checkedRadioButtonId
        if (id != -1) { // If any radio button checked from radio group
            // Get the instance of radio button using id
            val radio: RadioButton = findViewById(id)
            Toast.makeText(applicationContext, "On button click : ${radio.text}", Toast.LENGTH_SHORT).show()
            gender = "${radio.text}"
        } else {
            student_group.isClickable = false
        }
    }
    private fun checkEditTextIsEmpty() {
        if (edt_add_stdName.text.isEmpty()) {
            edt_add_stdName.error = "Required Student Name"
        } else if (edt_add_stdQualification.text.isEmpty()) {
            edt_add_stdQualification.error = "Required Student Qualification"
        } else if (edt_add_stdNrc.text.isEmpty()) {
            edt_add_stdNrc.error = "Required Student NRC"
        } else if (edt_add_stdPhone.text.isEmpty()) {
            edt_add_stdPhone.error = "Required Student Phone Number"
        } else if (edt_add_stdEmail.text.isEmpty()) {
            edt_add_stdEmail.error = "Required Student Email"
        } else if (edt_add_stdAddress.text.isEmpty()) {
            edt_add_stdAddress.error = "Required Student Address"
        } else if (edt_facebookStd.text.isEmpty()) {
            edt_facebookStd.error = "Required Student Facebook Link"
        }
    }
    private fun checkISDatePickerForDob() {
        // get the references from layout file
        button_date = this.button_date_1
        text_view_date_1!!.text = "Date of Birth"
        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }
        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        lin_dob!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@AddNewStudentActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })

    }
    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        text_view_date_1!!.text = sdf.format(cal.getTime())
        dob = text_view_date_1!!.text
        dob= sdf.format(cal.getTime())
    }
    private fun checkIsSpinnerForTrackAndBatch(){
        var trackArray = resources.getStringArray(R.array.track_name)
        //track spinner
        var trackArrayAdapter: allSpinnerAdapter = allSpinnerAdapter(this, trackArray, R.layout.text_spinner)
        s_track_spinner.adapter = trackArrayAdapter

        s_track_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddNewStudentActivity, "Ok", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                trackId = p2 + 1

            }

        }
        var batchArray = resources.getStringArray(R.array.batch_no)
        //bath spinner
        var bathArrayAdapter: allSpinnerAdapter = allSpinnerAdapter(this, batchArray, R.layout.text_spinner)
        s_batch_spinner.adapter = bathArrayAdapter

        s_batch_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddNewStudentActivity, "Ok", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@AddNewStudentActivity, trackArray[p2], Toast.LENGTH_SHORT).show()
                batchId = p2 + 1
            }

        }

    }
    private fun addNewStudent(){
        val apiCalls = RestAdapter.getClient().create(ApiService::class.java)
        val loginCall = apiCalls.sendStudentData(
            StudentData(
                image,
                edt_add_stdAddress.text.toString(),
                batchId,
                date ,
                edt_add_stdEmail.text.toString(),
                gender,
                edt_add_stdName.text.toString(),
                edt_add_stdNrc.text.toString(),
                edt_add_stdPhone.text.toString(),
                edt_add_stdQualification.text.toString(),
                trackId,
                edt_facebookStd.text.toString()
            )
        )
        loginCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("Errrrrrrrr Message:",t.localizedMessage)
                Toast.makeText(this@AddNewStudentActivity, "Network Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(this@AddNewStudentActivity, "Response Successful", Toast.LENGTH_SHORT).show()
                if (response.isSuccessful) {
                    finish()
                } else {
                    Toast.makeText(this@AddNewStudentActivity, "Add Failed", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
    private fun clearEditText(){

            edt_add_stdName.text.clear()

            edt_add_stdQualification.text.clear()

            edt_add_stdNrc.text.clear()

            edt_add_stdPhone.text.clear()

            edt_add_stdEmail.text.clear()

            edt_add_stdAddress.text.clear()

            edt_facebookStd.text.clear()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
