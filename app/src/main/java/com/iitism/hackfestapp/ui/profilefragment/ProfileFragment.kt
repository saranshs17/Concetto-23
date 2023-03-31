package com.iitism.hackfestapp.ui.profilefragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iitism.hackfestapp.R
import com.iitism.hackfestapp.WebViewActivity
import com.iitism.hackfestapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_profile, container, false)
        binding=FragmentProfileBinding.bind(view)


        val sharedPref=this.activity?.getSharedPreferences("myPref",Context.MODE_PRIVATE)
        binding.nameText.text=sharedPref?.getString("teamName","")
        Log.d("sharedPref",sharedPref?.getString("teamName","").toString())
        binding.Email.text=sharedPref?.getString("email","")
        binding.Organization.text=sharedPref?.getString("playerOrganization","")
        binding.Mobile.text=sharedPref?.getLong("playerMobile",0).toString()
        binding.Problem.text=sharedPref?.getString("problemStatement","")

//        binding.Problem.setOnClickListener {
//            startActivity(Intent(context,WebViewActivity::class.java))
//        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)



    }

}