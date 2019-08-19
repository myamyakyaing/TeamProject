package com.example.teamproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.teamproject.R
import com.example.teamproject.models.Teacher
import com.example.teamproject.ui.adapters.RecyclerTeacherAdapter
import kotlinx.android.synthetic.main.activity_teacher_list.*
import kotlinx.android.synthetic.main.tech_top_bar.*

class TeacherListActivity : AppCompatActivity() {
    lateinit var teacherArray: List<Teacher>

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_teacher_list)
        setSupportActionBar(techBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var teacher_tza = Teacher(
            "Ma Thaw Zin Aung", "Android", R.drawable.tza,
            "Android Developer", "Hlaing Township", "12/17/1992", "09 796879659", "tza@gmail.com"
        )
        var teacher_msp = Teacher(
            "Ko Myo Set Paing", "Android", R.drawable.msp,
            "Android Developer", "Sauth Dogan", "12/17/1992", "09 796879659", "msp@gmail.com"
        )
        var teacher_pla = Teacher(
            "Ko Phone Lin Aung", "Android", R.drawable.pla,
            "Android Developer", "North Dagon", "12/17/1992", "09 796879659", "pla@gmail.com"
        )
        var teacher_kkk = Teacher(
            "Ko Kyaw Kyaw Khaing", "Android", R.drawable.kkk,
            "Android Developer", "Sangaung Township", "12/17/1992", "09 796879659", "kkk@gmail.com"
        )
        teacherArray = mutableListOf<Teacher>(teacher_tza, teacher_msp, teacher_pla, teacher_kkk)
        val rvTeacherAdapter: RecyclerTeacherAdapter = RecyclerTeacherAdapter(teacherArray, this::onClickItem)
        rvTeacher.layoutManager = GridLayoutManager(this, 2)
        rvTeacher.adapter = rvTeacherAdapter
    }

    private fun onClickItem(teacher: Teacher) {
        startActivity(
            TeacherProfileActivity.newIntent(
                this, teacher.name, teacher.image, teacher.profession,
                teacher.address, teacher.dob, teacher.phone, teacher.email
            )
        )
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
            val intent = Intent(this,AddNewTrainerActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
