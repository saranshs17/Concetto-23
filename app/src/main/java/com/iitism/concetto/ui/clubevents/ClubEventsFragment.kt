package com.iitism.concetto.ui.clubevents

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R
import com.iitism.concetto.databinding.FragmentAllEventsBinding
import com.iitism.concetto.databinding.FragmentClubEventsBinding
import com.iitism.concetto.ui.allevents.AllEventsAdapter
import com.iitism.concetto.ui.allevents.AllEventsFragment
import com.iitism.concetto.ui.allevents.AllEventsViewModel
import com.iitism.concetto.ui.allevents.retrofit.RetrofitInstanceEvents
import com.iitism.concetto.ui.departmentevents.DepartmentAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class ClubEventsFragment : Fragment() {

    companion object {
        fun newInstance() = AllEventsFragment()

    }

    private val clubAdapter = ClubAdapter()
    private lateinit var viewModel: ClubEventsViewModel
    lateinit var retrofitInstance : RetrofitInstanceClubEvents
    private lateinit var binding : FragmentClubEventsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentClubEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retrofitInstance = RetrofitInstanceClubEvents()
        viewModel = ViewModelProvider(this)[ClubEventsViewModel::class.java]


        binding.rvClub.layoutManager = LinearLayoutManager(requireContext())
        // itemAdapter = AllEventsAdapter()
        binding.rvClub.adapter = clubAdapter
        binding.rvClub.setHasFixedSize(true)
        binding.loadingCardClubevents.visibility = View.VISIBLE
        // itemAdapter.notifyDataSetChanged()
        getEvents()
    }

    private fun getEvents()
    {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getClubEvents()
            this.launch(Dispatchers.Main) {
                binding.loadingCardClubevents.visibility = View.GONE
                clubAdapter.seteventList(viewModel.ClubEventList)
                Log.i("Data",viewModel.ClubEventList.toString())
            }
        }
    }




}