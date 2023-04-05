package com.iitism.hackfestapp.ui.noticeboardfragment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iitism.hackfestapp.databinding.FragmentNoticeBoardBinding
import com.iitism.hackfestapp.ui.aboutus.AboutUsRepository
import com.iitism.hackfestapp.ui.aboutus.retrofit.AboutUsViewModelFactoy
import com.iitism.hackfestapp.ui.aboutus.retrofit.RetrofitInstance
import com.iitism.hackfestapp.ui.noticeboardfragment.retrofit.NoticeBoardViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoticeBoardFragment : Fragment() {

    companion object {
        fun newInstance() = NoticeBoardFragment()
    }

    private lateinit var adapter: NoticeBoardAdapter
    private lateinit var viewModel: NoticeBoardViewModel
    private lateinit var binding: FragmentNoticeBoardBinding
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNoticeBoardBinding.inflate(inflater)
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading ...")
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        progressDialog.show()
        binding.loadingCard.loadingCard.visibility = View.VISIBLE
        viewModel = ViewModelProvider(
            this, NoticeBoardViewModelFactory(
                AboutUsRepository(
                    RetrofitInstance.api
                ),
                requireContext()
            )).get(NoticeBoardViewModel::class.java)
        adapter = NoticeBoardAdapter()
        binding.recyclerView.adapter = adapter

        networkCheckAndRun()
        binding.retryButton.setOnClickListener {
            networkCheckAndRun()
        }



    }

    fun networkCheckAndRun(){
        if(viewModel.isNetworkAvailable()){
            binding.retryButton.visibility = View.GONE
            binding.loadingCard.loadingCard.visibility = View.VISIBLE
            GlobalScope.launch(Dispatchers.IO) {
                viewModel.getAllOrganizers()
                this.launch(Dispatchers.Main) {
                    adapter.setNotices(viewModel.list)
                    binding.loadingCard.loadingCard.visibility = View.GONE
                }
            }
        }
        else{
            binding.retryButton.visibility = View.VISIBLE
            Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
            binding.loadingCard.loadingCard.visibility = View.GONE
        }
    }

}