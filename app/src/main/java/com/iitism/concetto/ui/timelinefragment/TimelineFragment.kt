package com.iitism.concetto.ui.timelinefragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R

class TimelineFragment : Fragment() {

    companion object {
        fun newInstance() = TimelineFragment()
    }

    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView= view?.findViewById(R.id.rv_timeline) ?: recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        val timelineList = arrayListOf( Pair("hi","bye"),
            Pair("how are you","I am fine") ,
            Pair("HackFest B","3 October 2023 1:00PM"),
            Pair("HackFest Be","3 October 2023 2:00PM"),
            Pair("HackFest Beg","3 October 2023 3:00PM"),
            Pair("HackFest Begi","3 October 2023 4:00PM"),
            Pair("HackFest Begin","3 October 2023 5:00PM"),
            Pair("HackFest Begins","3 October 2023 6:00PM")

        )

        recyclerView.adapter = timelineAdapter(timelineList)

    }

}