package com.iitism.concetto.ui.allevents

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.iitism.concetto.databinding.FragmentAllEventsBinding
import com.iitism.concetto.ui.aboutUs.AboutUsRepository
import com.iitism.concetto.ui.aboutUs.AboutUsViewModel
import com.iitism.concetto.ui.aboutUs.retrofit.AboutUsViewModelFactoy
import com.iitism.concetto.ui.aboutUs.retrofit.RetrofitInstance
import com.iitism.concetto.ui.allevents.retrofit.RetrofitInstanceEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retrofitInstance = RetrofitInstanceEvents()
        viewModel = ViewModelProvider(this)[AllEventsViewModel::class.java]


        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())

        // itemAdapter = AllEventsAdapter()

        binding.rvEvents.adapter = itemAdapter
        binding.rvEvents.setHasFixedSize(true)

        // itemAdapter.notifyDataSetChanged()

        getEvents()
    }

    private fun getEvents()
    {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getAllEvents()
            this.launch(Dispatchers.Main) {
                itemAdapter.seteventList(viewModel.EventsList)
                Log.i("Data",viewModel.EventsList.toString())
            }
        }
    }




}