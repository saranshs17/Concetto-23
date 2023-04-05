package com.iitism.hackfestapp.ui.aboutus

import android.app.ProgressDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.iitism.hackfestapp.databinding.FragmentAboutUsBinding
import com.iitism.hackfestapp.ui.aboutus.retrofit.AboutUsViewModelFactoy
import com.iitism.hackfestapp.ui.aboutus.retrofit.RetrofitInstance
import kotlinx.coroutines.*

class AboutUsFragment : Fragment() {

    companion object {
        fun newInstance() = AboutUsFragment()
    }

    private val adapter = AboutUsAdapter()
    private lateinit var binding : FragmentAboutUsBinding
    private lateinit var viewModel: AboutUsViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutUsBinding.inflate(inflater)
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading ...")
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.loadingCard.visibility = View.VISIBLE
        viewModel = ViewModelProvider(this,AboutUsViewModelFactoy(AboutUsRepository(RetrofitInstance.api),requireContext()))[AboutUsViewModel::class.java]
        binding.recyclerView.adapter = adapter
        networkCheckAndRun()
        binding.retryButton.setOnClickListener {
            networkCheckAndRun()
        }


    }


    fun networkCheckAndRun(){
        if(viewModel.isNetworkAvailable()){
            binding.loadingCard.visibility = View.VISIBLE
            getAllOrganizers()
        }
        else{
            Toast.makeText(context, "Network Error",Toast.LENGTH_SHORT).show()
            binding.loadingCard.visibility = View.GONE
            binding.retryButton.visibility = View.VISIBLE
        }
    }


    fun getAllOrganizers(){
        binding.retryButton.visibility = View.GONE
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getAllOrganizers()
            this.launch(Dispatchers.Main) {
                adapter.setorganizerList(viewModel.organizerList)
                binding.loadingCard.visibility = View.GONE
            }
        }
    }

}