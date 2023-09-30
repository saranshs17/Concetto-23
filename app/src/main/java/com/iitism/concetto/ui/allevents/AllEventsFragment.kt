package com.iitism.concetto.ui.allevents

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.iitism.concetto.R
import com.iitism.concetto.databinding.FragmentAllEventsBinding
import com.iitism.concetto.ui.allevents.retrofit.RetrofitInstanceEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.zip.Inflater

class AllEventsFragment : Fragment() {

    companion object {
        fun newInstance() = AllEventsFragment()
    }

    private lateinit var viewModel: AllEventsViewModel
    lateinit var retrofitInstance : RetrofitInstanceEvents
    private lateinit var binding : FragmentAllEventsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllEventsBinding.inflate(inflater, container, false)
        retrofitInstance = RetrofitInstanceEvents()
        viewModel = ViewModelProvider(this).get(AllEventsViewModel::class.java)
        getEvents()


        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        val itemAdapter = AllEventsAdapter(viewModel.EventsList)
        binding.rvEvents.adapter = itemAdapter
        binding.rvEvents.setHasFixedSize(true)
        itemAdapter.notifyDataSetChanged()
        return binding.root
    }

    fun getEvents()
    {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getAllEvents()
            this.launch(Dispatchers.Main) {
//                adapter.setorganizerList(viewModel.EventsList)
                Log.i("Data",viewModel.EventsList.toString())
            }
        }
    }




}