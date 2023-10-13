package com.iitism.concetto.ui.allevents

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.iitism.concetto.databinding.FragmentAllEventsBinding
import com.iitism.concetto.ui.aboutUs.AboutUsRepository
import com.iitism.concetto.ui.aboutUs.AboutUsViewModel
import com.iitism.concetto.ui.aboutUs.retrofit.AboutUsViewModelFactoy
import com.iitism.concetto.ui.aboutUs.retrofit.RetrofitInstance
import com.iitism.concetto.ui.allevents.retrofit.AllEventsDataModel
import com.iitism.concetto.ui.allevents.retrofit.RetrofitInstanceEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class AllEventsFragment : Fragment() {

    companion object {
        fun newInstance() = AllEventsFragment()
    }

    private val itemAdapter = AllEventsAdapter()
    private lateinit var viewModel: AllEventsViewModel
    lateinit var retrofitInstance : RetrofitInstanceEvents
    private lateinit var binding : FragmentAllEventsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentAllEventsBinding.inflate(inflater, container, false)
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retrofitInstance = RetrofitInstanceEvents()
        viewModel = ViewModelProvider(this)[AllEventsViewModel::class.java]


        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())

        // itemAdapter = AllEventsAdapter()

        binding.rvEvents.adapter = itemAdapter
        binding.rvEvents.setHasFixedSize(true)
        // itemdapter.notifyDataSetChanged()
        binding.loadingCardAllevents.visibility = View.VISIBLE

//        getEvents()

        if (isNetworkAvailable()) {
            getEvents()
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    searchList(newText)
                    return true
                }
            })
        } else {
            binding.loadingCardAllevents.visibility = View.VISIBLE
            binding.searchView.visibility = View.GONE
            Toast.makeText(requireContext(), "Network unavailable. Please try again.", Toast.LENGTH_LONG).show()
        }


    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private fun getEvents()
    {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getAllEvents()
            delay(2000)
            this.launch(Dispatchers.Main) {
                binding.loadingCardAllevents.visibility = View.GONE
                itemAdapter.seteventList(viewModel.EventsList)
                Log.i("Data",viewModel.EventsList.toString())
            }
        }
    }

    fun searchList(text: String) {

        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getAllEvents()
            delay(2000)
            this.launch(Dispatchers.Main) {
                binding.loadingCardAllevents.visibility = View.GONE
                val searchList = ArrayList<AllEventsDataModel>()
                //val dataClass = itemSnapshot.getValue(UserId::class.java)

                for (dataClass in viewModel.EventsList.value!!.toMutableList()) {
                    if (dataClass.name?.lowercase()
                            ?.contains(text.lowercase(Locale.getDefault())) == true
                        || dataClass.organizer?.lowercase()
                            ?.contains(text.lowercase(Locale.getDefault())) == true
                    ) {
                        searchList.add(dataClass)
                    }
                }
                itemAdapter.searchDataList(searchList)
            }
        }


    }

}