package com.example.vulnerablesmsapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import com.example.vulnerablesmsapp.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //TODO reason for using a content provider "You want to expose your application data to widgets."
    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO need to check and ask for sms permission!!
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view_pager.adapter = SectionsPagerAdapter(this)
        TabLayoutMediator(tabs, view_pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Messages"
                }
                1 -> {
                    tab.text = "New Message"
                }
            }
        }.attach()
        getSMSPermissions()
    }

    fun getSMSPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.RECEIVE_SMS
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.SEND_SMS
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            //If the app does not have permissions then ask for them.
            this.let {
                requestPermissions(
                        arrayOf(Manifest.permission.RECEIVE_SMS),
                        1
                )
            }
        }
    }
}