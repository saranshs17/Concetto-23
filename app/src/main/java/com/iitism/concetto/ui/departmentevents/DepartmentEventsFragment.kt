package com.iitism.concetto.ui.departmentevents

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
import com.iitism.concetto.databinding.FragmentClubEventsBinding
import com.iitism.concetto.databinding.FragmentDepartmentEventsBinding
import com.iitism.concetto.ui.aboutUs.retrofit.RetrofitInstance
import com.iitism.concetto.ui.allevents.AllEventsFragment
import com.iitism.concetto.ui.allevents.AllEventsViewModel
import com.iitism.concetto.ui.allevents.retrofit.RetrofitInstanceEvents
import com.iitism.concetto.ui.clubevents.ClubAdapter
import com.iitism.concetto.ui.clubevents.ClubEventsViewModel
import com.iitism.concetto.ui.clubevents.RetrofitInstanceClubEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class DepartmentEventsFragment : Fragment() {

    companion object {
        fun newInstance() = AllEventsFragment()

    }

    private val clubAdapter = DepartmentAdapter()
    private lateinit var viewModel: AllEventsViewModel
    lateinit var retrofitInstance : RetrofitInstanceEvents
    private lateinit var binding : FragmentDepartmentEventsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentDepartmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retrofitInstance = RetrofitInstanceEvents()
        viewModel = ViewModelProvider(this)[AllEventsViewModel::class.java]


        binding.rvDepartments.layoutManager = LinearLayoutManager(requireContext())
        // itemAdapter = AllEventsAdapter()
        binding.rvDepartments.adapter = clubAdapter
        binding.rvDepartments.setHasFixedSize(true)
        binding.loadingCardDepartmentevents.visibility = View.VISIBLE
        // itemAdapter.notifyDataSetChanged()
        getEvents()
    }

    private fun getEvents()
    {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getAllEvents()

            this.launch(Dispatchers.Main) {
                binding.loadingCardDepartmentevents.visibility = View.GONE
                clubAdapter.seteventList(viewModel.DepartmentEventList)
                Log.i("Data",viewModel.DepartmentEventList.toString())
            }
        }
    }


}