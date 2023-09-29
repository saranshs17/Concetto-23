package com.iitism.concetto.ui.departmentevents

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iitism.concetto.R

class DepartmentEventsFragment : Fragment() {

    companion object {
        fun newInstance() = DepartmentEventsFragment()
    }

    private lateinit var viewModel: DepartmentEventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_department_events, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DepartmentEventsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}