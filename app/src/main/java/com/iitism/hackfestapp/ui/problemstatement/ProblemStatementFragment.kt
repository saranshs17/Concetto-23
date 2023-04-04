package com.iitism.hackfestapp.ui.problemstatement

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.iitism.hackfestapp.R
import com.iitism.hackfestapp.databinding.FragmentNoticeBoardBinding
import com.iitism.hackfestapp.databinding.FragmentProblemStatementBinding
import com.iitism.hackfestapp.ui.aboutus.AboutUsRepository
import com.iitism.hackfestapp.ui.aboutus.retrofit.RetrofitInstance
import com.iitism.hackfestapp.ui.noticeboardfragment.NoticeBoardAdapter
import com.iitism.hackfestapp.ui.noticeboardfragment.NoticeBoardViewModel
import com.iitism.hackfestapp.ui.noticeboardfragment.retrofit.NoticeBoardViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.combine
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