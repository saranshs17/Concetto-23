package com.iitism.concetto.ui.problemstatement

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iitism.concetto.databinding.FragmentProblemStatementBinding
import com.iitism.concetto.ui.aboutus.AboutUsRepository
import com.iitism.concetto.ui.aboutus.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProblemStatementFragment : Fragment() {

    companion object {
        fun newInstance() = ProblemStatementFragment()
    }

    private lateinit var adapter: ProblemStatementAdapter
    private lateinit var viewModel: ProblemStatementViewModel
    private lateinit var binding: FragmentProblemStatementBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProblemStatementBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.loadingCard.loadingCard.visibility = View.VISIBLE
        viewModel = ViewModelProvider(
            this, ProblemStatementViewModelFactory(
                AboutUsRepository(
                    RetrofitInstance.api
                )
            )
        ).get(ProblemStatementViewModel::class.java)
        adapter = ProblemStatementAdapter()
        binding.recyclerView.adapter = adapter

        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getAllProblems()
            this.launch(Dispatchers.Main) {
                adapter.setProblems(viewModel.list)
                binding.loadingCard.loadingCard.visibility = View.GONE
            }

        }
    }

}