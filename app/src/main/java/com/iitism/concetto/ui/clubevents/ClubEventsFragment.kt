package com.iitism.concetto.ui.clubevents

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R
import com.iitism.concetto.ui.departmentevents.DepartmentAdapter
import kotlin.properties.Delegates

class ClubEventsFragment : Fragment() {

    companion object {
        fun newInstance() = ClubEventsFragment()
    }

    private lateinit var viewModel: ClubEventsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ClubAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.fragment_club_events, container, false)
        recyclerView = view.findViewById(R.id.rv_club)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        var obj =  Club_dataclass(
        "651588ec97393f90ccc9f01b",
        "fwufygdvhdbveu",
        "scwjbcjwhf",
        "Robowar",
        "offline",
        "dvwuyvuewivuauerogvyugviegr",
        "efjkwevhrihvehgrei",
        2,
        "RoboISM",
        listOf("Rule 1", "Rule 2"),
        "Prize worth 50k",
        listOf(
            Contact("6517e5fd57667dacc4f21822", "Ram", "4684149"),
            Contact("6517e5fd57667dacc4f21823", "Shyam", "489817979")
        ),
        "sfwgfewgyfwehe",
        2,
        5,
        "this is the problemstatement",
        listOf(
            ExtraDetail(
                "6517e5fd57667dacc4f21824",
                "dfw efh",
                listOf("efbefweujwhufiwhef", "efwifhuw", "efcwehgfuwgey")
            ),
            ExtraDetail(
                "6517e5fd57667dacc4f21825",
                "rwuhi efh",
                listOf("efbefweujwhufir23uhi2urwhef", "ecwhefguwf", "wefwhuegfy")
            )
        ),
        listOf("651591b778a6ce2f30cf8292"),
        listOf(
            Stage(
                "6517e5fd57667dacc4f21826",
                "This is description",
                "OAT",
                "5 pm",
                "this is calender link"
            )
        )
        )

        var eventList= listOf(obj)
        obj.posterMobile = "aga"
        adapter=ClubAdapter(eventList)

        recyclerView.adapter = adapter
        return view
    }


}