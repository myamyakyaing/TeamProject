package com.example.teamproject.ui.activities

//import com.example.teamproject.ui.adapters.batchSpinnerAdapter
//import com.example.teamproject.ui.adapters.trackSpinnerAdapter
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.util.Log.i
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.teamproject.R
import com.example.teamproject.models.TrainerData
import com.example.teamproject.network.ApiService
import com.example.teamproject.network.RestAdapter
import com.example.teamproject.ui.adapters.allSpinnerAdapter
import kotlinx.android.synthetic.main.activity_add_new_trainer.*
import kotlinx.android.synthetic.main.new_teacher_bar.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddNewTrainerActivity : AppCompatActivity() {
    val REQUEST_IMAGE_GET = 1
    lateinit var dob: CharSequence
    var date = ""
    var button_date: ImageButton? = null
    var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    var trackId: Int = 0
    var courseId: Int = 0
    var batchId: Int = 0
    var gender: String = ""
    var image: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_new_trainer)
        setSupportActionBar(newTechBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        checkISDatePickerForDob()
        checkIsSpinnerForTrackAndBatch()
        checkUploadImage()

        btn_trainer_save.setOnClickListener {
            checkEditTextIsEmpty()
            radio_button_click()
            date = dob.toString()
            addNewTrainer()
            clearEditText()
        }
    }

    private fun checkUploadImage() {
        add_imgTrainer.setOnClickListener {
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
            Glide.with(this).load(fullPhotoUri).into(add_imgTrainer)
            val bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fullPhotoUri)
            add_imgTrainer.setImageBitmap(bitmap)
            val imgTrianer = add_imgTrainer.getDrawable()
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
        var id: Int = trainer_group.checkedRadioButtonId
        if (id != -1) { // If any radio button checked from radio group
            // Get the instance of radio button using id
            val radio: RadioButton = findViewById(id)
            gender = "${radio.text}"
            Toast.makeText(applicationContext, gender, Toast.LENGTH_SHORT).show()
        } else {
            trainer_group.isClickable = false
        }
    }

    private fun checkEditTextIsEmpty() {
        if (edt_add_techName.text.isEmpty()) {
            edt_add_techName.error = "Required Student Name"
        } else if (edt_add_techPosition.text.isEmpty()) {
            edt_add_techPosition.error = "Required Student Qualification"
        } else if (edt_add_techNrc.text.isEmpty()) {
            edt_add_techNrc.error = "Required Student NRC"
        } else if (edt_add_techPhone.text.isEmpty()) {
            edt_add_techPhone.error = "Required Student Phone Number"
        } else if (edt_add_techEmail.text.isEmpty()) {
            edt_add_techEmail.error = "Required Student Email"
        } else if (edt_add_techAddress.text.isEmpty()) {
            edt_add_techAddress.error = "Required Student Address"
        } else if (edt_add_techCompany.text.isEmpty()) {
            edt_add_techCompany.error = "Required Student Facebook Link"
        }
    }

    private fun checkISDatePickerForDob() {
        // get the references from layout file
        textview_date = this.text_view_date_2
        button_date = this.button_date_2
        textview_date!!.text = "Date of Birth"
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
        lin_trainer_dob!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@AddNewTrainerActivity,
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
        textview_date!!.text = sdf.format(cal.getTime())
        dob = textview_date!!.text
        dob = sdf.format(cal.getTime())
    }

    private fun checkIsSpinnerForTrackAndBatch() {
        //course spinner
        var courseArray = resources.getStringArray(R.array.course_name)
        var courseArrayAdapter: allSpinnerAdapter = allSpinnerAdapter(this, courseArray, R.layout.text_spinner)
        t_course_spinner.adapter = courseArrayAdapter

        t_course_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddNewTrainerActivity, "Ok", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                courseId = p2 + 1

            }

        }
        //track spinner
        var trackArray = resources.getStringArray(R.array.track_name)
        var trackArrayAdapter: allSpinnerAdapter = allSpinnerAdapter(this, trackArray, R.layout.text_spinner)
        t_track_spinner.adapter = trackArrayAdapter

        t_track_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddNewTrainerActivity, "Ok", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                trackId = p2 + 1

            }

        }
        //bath spinner
        var batchArray = resources.getStringArray(R.array.batch_no)
        var bathArrayAdapter: allSpinnerAdapter = allSpinnerAdapter(this, batchArray, R.layout.text_spinner)
        t_batch_spinner.adapter = bathArrayAdapter

        t_batch_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddNewTrainerActivity, "Ok", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                batchId = p2 + 1
            }

        }

    }

    private fun addNewTrainer() {
        val apiCalls = RestAdapter.getClient().create(ApiService::class.java)
        val loginCall = apiCalls.sendTrainerData(
            TrainerData(
                edt_add_techAddress.text.toString(),
                batchId,
                date,
                edt_add_techEmail.text.toString(),
                gender,
                edt_add_techName.text.toString(),
                edt_add_techNrc.text.toString(),
                edt_add_techPhone.text.toString(),
                edt_add_techPosition.text.toString(),
                trackId,
                edt_add_techCompany.text.toString(),
                image,
                courseId
            )
        )
        loginCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@AddNewTrainerActivity, "Response Failed", Toast.LENGTH_SHORT).show()
                Log.d("ADD TRAINER ERROR", t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                i("ResponseCode", response.code().toString())

                if (response.isSuccessful) {
                    finish()
                } else {
                    Toast.makeText(this@AddNewTrainerActivity, "Add Failed", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun clearEditText() {

        edt_add_techName.text.clear()

        edt_add_techPosition.text.clear()

        edt_add_techNrc.text.clear()

        edt_add_techPhone.text.clear()

        edt_add_techEmail.text.clear()

        edt_add_techAddress.text.clear()

        edt_add_techCompany.text.clear()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
