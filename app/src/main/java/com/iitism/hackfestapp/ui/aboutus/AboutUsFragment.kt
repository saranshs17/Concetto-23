package com.iitism.hackfestapp.ui.aboutus

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutUsBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this,AboutUsViewModelFactoy(AboutUsRepository(RetrofitInstance.api)))
            .get(AboutUsViewModel::class.java)
        binding.recyclerView.adapter = adapter

        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getAllOrganizers()
            this.launch(Dispatchers.Main) {
                adapter.setorganizerList(viewModel.organizerList)
            }
        }


    }

}