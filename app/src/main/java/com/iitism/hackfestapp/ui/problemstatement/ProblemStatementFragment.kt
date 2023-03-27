package com.iitism.hackfestapp.ui.problemstatement

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iitism.hackfestapp.R

class ProblemStatementFragment : Fragment() {

    companion object {
        fun newInstance() = ProblemStatementFragment()
    }

    private lateinit var viewModel: ProblemStatementViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_problem_statement, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProblemStatementViewModel::class.java)
        // TODO: Use the ViewModel
    }

}