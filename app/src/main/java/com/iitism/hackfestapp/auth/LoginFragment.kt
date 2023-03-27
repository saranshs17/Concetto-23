package com.iitism.hackfestapp.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.iitism.hackfestapp.MainActivity
import com.iitism.hackfestapp.auth.Refractor.LoginRepository
import com.iitism.hackfestapp.auth.Refractor.LoginViewModel
import com.iitism.hackfestapp.auth.Refractor.BaseFragment
import com.iitism.hackfestapp.databinding.FragmentLoginBinding
import com.iitism.hackfestapp.retrofit.AuthApiLogin
import com.iitism.hackfestapp.retrofit.Resource

class loginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success->{
                    Log.d("Login", it.value.message)
                    val user= User(it.value.data.Player_Email,it.value.data.Team_Name)
                    Toast.makeText(requireContext(),"Welcome Back to ${it.value.data.Team_Name}",Toast.LENGTH_LONG).show()
                    startActivity(Intent(context,MainActivity::class.java))
                }
                is Resource.Failure->{
                    Toast.makeText(requireContext(), it.errorBody.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.LoginButton.setOnClickListener{
            val progressBar=binding.loginProgressBar
            val visibility=if(progressBar.visibility==View.GONE) View.VISIBLE
            else View.GONE
            progressBar.visibility=visibility
            val teamName=binding.teamNameEditTextLogin.text.toString()
            val password=binding.passwordLogin.text.toString()
            if(teamName.isEmpty()){
                Toast.makeText(context,"Player Email is required",Toast.LENGTH_LONG).show()
            }else if(password.isEmpty()){
                Toast.makeText(context,"Password is required",Toast.LENGTH_LONG).show()
            }else{
                viewModel.login(teamName,password)
            }

        }


    }


    override fun getViewModel()=LoginViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) =FragmentLoginBinding.inflate(inflater,container,false)

    override fun getFragmentRepository()= LoginRepository(remoteDataSource.BuildApi(AuthApiLogin::class.java))


}