package com.example.teamproject.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.Window
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide.init
import com.example.teamproject.R
import com.example.teamproject.models.ImageModel
import com.example.teamproject.ui.adapters.SlidingImage_Adapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var imageModelArrayList: ArrayList<ImageModel>? = null

    companion object {

        private var mPager: ViewPager? = null
        private var currentPage = 0
        private var NUM_PAGES = 0
    }

    private val myImageList = intArrayOf(
        R.drawable.chess,
        R.drawable.discuss,
        R.drawable.business,
        R.drawable.uto,
        R.drawable.thinking,
        R.drawable.dat,
        R.drawable.presentation
    )

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
           }
            R.id.navigation_dashboard -> {
                val intent = Intent(this, SectionActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener false
            }
            R.id.navigation_notifications -> {
                val intent = Intent(this, AdminUserListActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener false
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

        bottom_nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()

        init()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }


    private fun populateList(): ArrayList<ImageModel> {

        val list = ArrayList<ImageModel>()

        for (i in 0..6) {
            val imageModel = ImageModel()
            imageModel.setImage_drawables(myImageList[i])
            list.add(imageModel)
        }

        return list
    }

    private fun init() {

        mPager = findViewById(R.id.pager) as ViewPager
        mPager!!.adapter = SlidingImage_Adapter(this@MainActivity, this.imageModelArrayList!!)

        val indicator = findViewById(R.id.indicator) as CirclePageIndicator

        indicator.setViewPager(mPager)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.setRadius(5 * density)

        NUM_PAGES = imageModelArrayList!!.size

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            mPager!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)

        // Pager listener over indicator
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                currentPage = position

            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {

            }

            override fun onPageScrollStateChanged(pos: Int) {

            }
        })

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_profile -> {
                val intent = Intent(this, MainProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_aboutas -> {

                val intent = Intent(this, AboutAsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_help -> {
                val intent = Intent(this, HelperActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
