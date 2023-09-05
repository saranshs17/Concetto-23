package com.iitism.concetto.ui.pastSponsors

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iitism.concetto.R

class PastSponsorsFragment : Fragment() {

    companion object {
        fun newInstance() = PastSponsorsFragment()
    }

    private lateinit var viewModel: PastSponsorsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_past_sponsors, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PastSponsorsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}