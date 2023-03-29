package com.iitism.hackfestapp.changePassword

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.room.InvalidationTracker
import com.iitism.hackfestapp.MainActivity
import com.iitism.hackfestapp.R
import com.iitism.hackfestapp.auth.Refractor.BaseFragment
import com.iitism.hackfestapp.auth.Refractor.LoginViewModel
import com.iitism.hackfestapp.auth.User
import com.iitism.hackfestapp.databinding.FragmentChangePasswordBinding
import com.iitism.hackfestapp.retrofit.Resource

class ChangePassword :BaseFragment<ChangeViewModel,FragmentChangePasswordBinding,ChangePassRespo>(){

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.changeResponse.observe(viewLifecycleOwner,Observer {
            when(it){
                is Resource.Success->{
                    Log.d("ChangePassword", it.value.message.toString())

                    Toast.makeText(requireContext(),"Password Changed",
                        Toast.LENGTH_LONG).show()
                    startActivity(Intent(context, MainActivity::class.java))
                }
                is Resource.Failure->{
                    Toast.makeText(requireContext(), it.errorBody.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.changePassButton.setOnClickListener {
            val email=binding.changePasswordEmailEdit.text.toString()
            val oldPassword=binding.OldPasswordEdit.text.toString()
            val newPassword=binding.NewPasswordEdit.text.toString()
            val progressBar=binding.changepasswordProgressBar
            val visibility=if(progressBar.visibility==View.GONE) View.VISIBLE
            else View.GONE
            progressBar.visibility=visibility

            if(email.isEmpty()){
                Toast.makeText(context,"Player Email is required",Toast.LENGTH_LONG).show()
            }else if(oldPassword.isEmpty()){
                Toast.makeText(context,"Old Password is required",Toast.LENGTH_LONG).show()
            }
            else if(newPassword.isEmpty()){
                Toast.makeText(context,"new Password is required",Toast.LENGTH_LONG).show()

            }else{
                viewModel.changepassword(email,oldPassword,newPassword)
            }
        }
    }
    override fun getViewModel()=ChangeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentChangePasswordBinding.inflate(inflater,container,false)

    override fun getFragmentRepository()= ChangePassRespo(remoteDataSource.BuildApi(PasswordChange::class.java))

}