package com.iitism.concetto.ui.departmentevents

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R
import kotlin.properties.Delegates

class DepartmentEventsFragment : Fragment() {

    companion object {
        fun newInstance() = DepartmentEventsFragment()
    }

    private lateinit var viewModel: DepartmentEventsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:DepartmentAdapter
    private lateinit var  posterMobile: String
    private lateinit var posterWeb: String
    private lateinit var name: String
    private lateinit var mode: String
    private lateinit var descriptionEvent: String
    private lateinit var descriptionOrganizer: String
    private var type by Delegates.notNull<Int>()
    private lateinit var organizer: String
    private lateinit var rules: List<String>
    private lateinit var prizes: String
    private lateinit var contacts: List<String>
    private var fees by Delegates.notNull<Int>()
    private lateinit var pdfLink: String
    private var minTeamSize by Delegates.notNull<Int>()
    private var maxTeamSize by Delegates.notNull<Int>()
    private lateinit var problemStatements: String
    private lateinit var extraDetails: List<String>
    private lateinit var teams: List<String>
    private lateinit var stages: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_department_events, container, false)
        recyclerView = view.findViewById(R.id.rv_departments)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)


        adapter=DepartmentAdapter(posterMobile,
            posterWeb,
            name,
            mode,
            descriptionEvent,
            descriptionOrganizer,
            type,
            organizer,
            rules,
        prizes,
        contacts,
        fees,
        pdfLink,
        minTeamSize,
        maxTeamSize,
        problemStatements,
        extraDetails,
        teams,
        stages)

        recyclerView.adapter = adapter

        return view
    }
}