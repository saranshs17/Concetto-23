package com.iitism.concetto.ui.singleevent

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
//import com.iitism.concett.RegisterActivity
import com.iitism.concetto.databinding.ActivityViewerBinding
import com.iitism.concetto.ui.registrationEvent.RegisterActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class ViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewerBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: FragmentPageAdapter
    private lateinit var viewModel :MyViewModel
    public  lateinit var eventType : String
    private var flag : Boolean = false

    interface ApiService {
        @GET
        suspend fun getEvent(
            @Url endpoint : String
        ): Response<SingleEventModel>
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding= ActivityViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerbtn.visibility = View.INVISIBLE
        val eventIdExtra = intent?.getStringExtra("eventID")
        if (eventIdExtra != null) {
            eventType = eventIdExtra
            viewModel = ViewModelProvider(this, MyViewModelFactory(this)).get(MyViewModel::class.java)
            Log.i("type", eventType)
            networkCheckAndRun()
        } else {
            // Handle the case where "eventID" extra is not provided in the Intent
            Toast.makeText(this, "Event ID is missing.", Toast.LENGTH_SHORT).show()
            // Optionally, you can finish this activity or take appropriate action.
            // For example:
            // finish()
        }

//        binding.retryButtonViwerActivity.setOnClickListener()
//        {
//            networkCheckAndRun()
//        }


    }


    fun startResgister()
    {
        Log.i("DAta",viewModel.EventsList.value?.get(0).toString())
        val maxParticipants : Int = viewModel.EventsList.value?.get(0)?.maxTeamSize ?: 0
        val minParticipants : Int = viewModel.EventsList.value?.get(0)?.minTeamSize ?: 0
        val id : String = viewModel.EventsList.value?.get(0)?._id.toString()
        val posterMobile : String =viewModel.EventsList.value?.get(0)?.posterMobile.toString()
        val eventName : String = viewModel.EventsList.value?.get(0)?.name.toString()
        val registationStatus : Int = viewModel.EventsList.value?.get(0)?.registrationStatus.toString().toInt()
        val registrationLink : String = viewModel.EventsList.value?.get(0)?.registrationLink.toString()
//        if(flag) {
            intent = Intent(
                this,
                RegisterActivity()::class.java
            )
            intent.putExtra("max", maxParticipants)
            intent.putExtra("min", minParticipants)
            intent.putExtra("id", id)
            intent.putExtra("posterUrl", posterMobile)
        intent.putExtra("EventName",eventName)

        if (registationStatus == 1)
        {
            binding.registerbtn.setOnClickListener()
            {
                startActivity(intent)
            }
        }
        else
        {
            if (registrationLink != null) {
                val url = registrationLink
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse(url)
//                if (intent.resolveActivity(packageManager) != null) {
//                    startActivity(intent)
                val intent = Intent(android.content.Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                intent.setPackage("com.android.chrome")
                Log.i("reg link",intent.data.toString())
                binding.registerbtn.setOnClickListener()
                {
                    startActivity(intent)
                }

                }

            else
                Toast.makeText(this, "No Registration Link  available", Toast.LENGTH_SHORT).show()
        }


    }

    public class RetrofitInstanceForSingleEvent {

        val client = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS) // Increase the connect timeout
            .readTimeout(30, TimeUnit.SECONDS)    // Increase the read timeout
            .writeTimeout(30, TimeUnit.SECONDS)   // Increase the write timeout
            .build()

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://concetto-backend-heli.onrender.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService : ApiService = retrofit.create(ApiService::class.java)
    }

    fun networkCheckAndRun(){
        if(viewModel.isNetworkAvailable()){
            binding.loadingCardViewerActitivity.visibility = View.VISIBLE
            fetchData()
        }
        else{
            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show()
            binding.loadingCardViewerActitivity.visibility = View.GONE
            binding.retryButtonViwerActivity.visibility = View.VISIBLE
        }
    }

    fun fetchData()
    {
        binding.retryButtonViwerActivity.visibility = View.GONE
         val retrofit: ViewerActivity.RetrofitInstanceForSingleEvent = ViewerActivity.RetrofitInstanceForSingleEvent()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                eventType = "api/showEvents/" + eventType
                val response: Response<SingleEventModel> = retrofit.apiService.getEvent(eventType)
                if (response.isSuccessful) {
                    delay(1500)
                    binding.loadingCardViewerActitivity.visibility = View.GONE
                    flag = true
                    val data = response.body()
                    if (data != null) {
                        // Data fetched successfully
                        Log.i("Data One Event", data.toString())
                        viewModel.EventsList.postValue(data)
                        adapter= FragmentPageAdapter(supportFragmentManager,lifecycle,viewModel)
                        tabLayout= binding.tabLayout
                        viewPager= binding.viewPager2
                        tabLayout.addTab(tabLayout.newTab().setText("ABOUT"))
                        tabLayout.addTab(tabLayout.newTab().setText("RULES"))
                        tabLayout.addTab(tabLayout.newTab().setText("DETAILS"))
                        binding.viewPager2.adapter = adapter
                        val compositePageTransformer = CompositePageTransformer()
                        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
                        compositePageTransformer.addTransformer { page, position ->
                            val r = 1 - abs(position)
                            page.scaleY = (0.80f + r * 0.20f)
                        }
                        viewPager.setPageTransformer(compositePageTransformer)
                        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                            override fun onTabSelected(tab: TabLayout.Tab?) {
                                if (tab!= null){
                                    viewPager.currentItem= tab.position
                                }
                            }
                            override fun onTabUnselected(tab: TabLayout.Tab?) {
                            }
                            override fun onTabReselected(tab: TabLayout.Tab?) {
                            }

                        })

                        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                            override fun onPageSelected(position: Int) {
                                super.onPageSelected(position)
                                tabLayout.selectTab(tabLayout.getTabAt(position))
                            }
                        })

//
                        delay(1500)
                        binding.registerbtn.visibility = View.VISIBLE
                        if(viewModel.EventsList != null) {
                            startResgister()
                        }


                    }
                } else {
                    Log.e("API Error", "Response code: ${response.code()}")
                }
            } catch (e: Exception) {
                // Handle network error or other exceptions here
                // For example, you can throw a custom exception or log the error
                Log.e("Error", e.toString())
            }
        }

    }

}

