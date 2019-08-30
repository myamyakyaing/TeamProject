package com.example.teamproject.ui.activities
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.example.teamproject.R
import com.example.teamproject.models.StudentList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_student_profile.*
import kotlinx.android.synthetic.main.student_profile.*

class StudentProfileActivity : AppCompatActivity() {

    companion object {
        val STUDENT_LIST = "student_list"
        fun newActivity(
            context: StudentListActivity,studentList: StudentList): Intent {
            val intent = Intent(context, StudentProfileActivity::class.java)
            intent.putExtra(STUDENT_LIST,studentList)
            return intent

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_student_profile)
        setSupportActionBar(student_profile_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val studentList = intent.extras!!.getSerializable(STUDENT_LIST) as StudentList
        Picasso.get().load(studentList.imageLink).into(imgStdProfile1)
        txtStdName.text = studentList.name
        txtStdProfession.text = studentList.qualification
        txtStdAddress.text = studentList.address
        txtStdDob.text = studentList.dob
        txtStdPhone.text = studentList.phone
        txtStdNrc.text = studentList.nrc
        txtStdGender.text = studentList.gender
        txtStdTrack.text = studentList.track!!.trackName
        txtStdBatch.text = studentList.batch!!.batchName
        txtStdEmail.text = studentList.email
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
