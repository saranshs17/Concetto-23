package com.iitism.concetto

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.iitism.concetto.databinding.ActivityMainBinding
import com.iitism.concetto.ui.fcm_service_package.fcm_api_service_package.NotificationService
import com.iitism.concetto.ui.fcm_service_package.token_api_service_package.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var tokenList:ArrayList<String>

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//Set portrait

        val playerEmail=intent.getStringExtra("playerEmail")
        Log.d("mainActivityData",playerEmail.toString())
        askNotificationPermission()
        val sharedPreferences=getSharedPreferences("TokenPreferences", MODE_PRIVATE)
        val savedToken=sharedPreferences.getString("SavedToken","Nothing")
        if(savedToken.equals("Nothing")){
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("Task Unsuccessful", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                val token = task.result.toString()
                Log.d("Your Device Token=>>>", token)
                val editPref:SharedPreferences.Editor=sharedPreferences.edit()
                editPref.putString("SavedToken",token)
                editPref.commit()
                ApiService().addTokenService(token,this)
//                val list=ArrayList<String>()
//                list.add(token)
            })
        }

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
                R.id.nav_coreTeam,
                R.id.nav_allEvents,
                R.id.nav_clubEvents,
                R.id.nav_departementEvents,
                R.id.nav_workshop,
//                R.id.nav_rules,
                R.id.nav_scanqr,
//                R.id.nav_gatepass,
                R.id.nav_maps,
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
                R.id.nav_coreTeam -> "CORE TEAM"
                R.id.nav_allEvents-> "ALL EVENTS"
                R.id.nav_clubEvents -> "CLUB EVENTS"
                R.id.nav_departementEvents -> "DEPARTMENT EVENTS"
                R.id.nav_workshop -> "WORKSHOPS"
                R.id.nav_maps-> "Maps"
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
        binding.navView.setCheckedItem(R.id.nav_home)
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
            //extract token

        } else {
            // TODO: Inform user that that your app will not show notifications.
            Toast.makeText(this,"Notifications will not be shown",Toast.LENGTH_SHORT).show()
        }
    }
    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            }
//            else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                // TODO: display an educational UI explaining to the user the features that will be enabled
//                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
//                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
//                //       If the user selects "No thanks," allow the user to continue without notifications.
//            }
            else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

}
