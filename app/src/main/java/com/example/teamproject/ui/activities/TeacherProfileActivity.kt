package com.example.teamproject.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.teamproject.R
import com.example.teamproject.models.Trainer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_teacher_profile.*
import kotlinx.android.synthetic.main.new_teacher_bar.*
import kotlinx.android.synthetic.main.rv_teacher.*
import kotlinx.android.synthetic.main.teacher_profile.*

class TeacherProfileActivity : AppCompatActivity() {

    companion object {
        val TRAINER_LIST = "trainer_list"
        fun newActivity(
            context: TeacherListActivity,trainer: Trainer): Intent {
            val intent = Intent(context, TeacherProfileActivity::class.java)
            intent.putExtra(TRAINER_LIST,trainer)
            return intent

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_teacher_profile)
        setSupportActionBar(techProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val trainer_list = intent.extras!!.getSerializable(TRAINER_LIST) as Trainer
        Picasso.get().load(trainer_list.imageLink).into(imgTeacherProfile1)
        txtTrainerName.text = trainer_list.name
        txtTechProfession.text = trainer_list.position
        txtTechAddress.text = trainer_list.address
        txtTechDob.text = trainer_list.dob
        txtTechPhone.text = trainer_list.phone
        txtTechNrc.text = trainer_list.nrc
        txtTechGender.text = trainer_list.gender
        txtTechTrack.text = trainer_list.track!!.trackName
        txtTechBatch.text = trainer_list.batch!!.batchName
        txtTechEmail.text = trainer_list.email
        txtTechOffice.text = trainer_list.company

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
