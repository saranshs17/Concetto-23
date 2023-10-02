package com.iitism.concetto.ui.coreteam

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R
import retrofit2.http.Url

class CoreTeamFragment : Fragment() {


    private lateinit var viewModel: CoreTeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_core_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val CoreTeamRecyclerView : RecyclerView = view.findViewById(R.id.recycler_view_c)

        CoreTeamRecyclerView.layoutManager = LinearLayoutManager(context)
       CoreTeamRecyclerView.setHasFixedSize(true)

        viewModel = CoreTeamViewModel(requireContext())
        viewModel.getCoreTeamList()

        val itemAdapter = CoreTeamAdapter(viewModel.coreTeamList)
        CoreTeamRecyclerView.adapter = itemAdapter

    }

}