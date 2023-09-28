package com.iitism.concetto.ui.coreteam

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iitism.concetto.R

class CoreTeamFragment : Fragment() {

    companion object {
        fun newInstance() = CoreTeamFragment()
    }

    private lateinit var viewModel: CoreTeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_core_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CoreTeamViewModel::class.java)
        // TODO: Use the ViewModel
    }

}