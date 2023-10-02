package com.iitism.concetto.ui.singleevent

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.iitism.concetto.R
import com.iitism.concetto.databinding.ActivityViewerBinding
import com.iitism.concetto.ui.RegisterActivity
import com.iitism.concetto.ui.allevents.AllEventsViewModel
import com.iitism.concetto.ui.allevents.retrofit.AllEventsApiService
import com.iitism.concetto.ui.allevents.retrofit.AllEventsDataModel
import com.iitism.concetto.ui.allevents.retrofit.RetrofitInstanceEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

class ViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewerBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: FragmentPageAdapter
    private lateinit var viewModel :MyViewModel
    public  lateinit var eventType : String


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
        eventType = intent.getStringExtra("eventID").toString()

        viewModel = ViewModelProvider(this,MyViewModelFactory(this)).get(MyViewModel::class.java)
        Log.i("type",eventType)
        if(intent != null)
          networkCheckAndRun()

        binding.retryButtonViwerActivity.setOnClickListener()
        {
            networkCheckAndRun()
        }

        val maxParticipants : Int = viewModel.EventsList.value?.get(0)?.maxTeamSize ?: 0
        val minParticipants : Int = viewModel.EventsList.value?.get(0)?.minTeamSize ?: 0

        intent = Intent(this,RegisterActivity::class.java)
        intent.putExtra("max",maxParticipants)
        intent.putExtra("min",minParticipants)
    }

    public class RetrofitInstanceForSingleEvent {

        val client = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS) // Increase the connect timeout
            .readTimeout(30, TimeUnit.SECONDS)    // Increase the read timeout
            .writeTimeout(30, TimeUnit.SECONDS)   // Increase the write timeout
            .build()

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://concetto-backend-clone.onrender.com/")
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
                    binding.loadingCardViewerActitivity.visibility = View.GONE
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

