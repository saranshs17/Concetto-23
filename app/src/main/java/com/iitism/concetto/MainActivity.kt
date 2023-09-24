package com.iitism.concetto

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.iitism.concetto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playerEmail=intent.getStringExtra("playerEmail")
        Log.d("mainActivityData",playerEmail.toString())




        val drawerLayout:DrawerLayout  = binding.drawerLayout
        val navView:NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_profile,
                R.id.nav_timeline,
                R.id.nav_noticeboard,
                R.id.nav_aboutus,
                R.id.nav_contactus,
//                R.id.nav_rules,
                R.id.nav_scanqr,
//                R.id.nav_gatepass,
                R.id.nav_merchandise,
                R.id.nav_sponsors
            ),
            drawerLayout
        )
        binding.appBarMain.menuButton.setOnClickListener{
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.appBarMain.titleactionbar.text = when (destination.id) {
                R.id.nav_profile -> "PROFILE"
                R.id.nav_aboutus -> "ABOUT US"
                R.id.nav_contactus -> "CONTACT US"
                R.id.nav_noticeboard -> "NOTICE BOARD"
                R.id.nav_timeline -> "TIMELINE"
                R.id.nav_scanqr -> "SCAN QR"
                R.id.nav_home -> "HOME"
                R.id.nav_merchandise -> "MERCHANDISE"
//                R.id.nav_gatepass -> "GATE PASS"
                R.id.nav_sponsors -> "PAST SPONSORS"
                else -> "Concetto'23"
            }


        }
        navView.setupWithNavController(navController)
        navView.setCheckedItem(R.id.nav_home)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }





}