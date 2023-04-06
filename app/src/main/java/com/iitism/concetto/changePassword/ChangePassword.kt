package com.iitism.concetto.changePassword

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.iitism.concetto.MainActivity
import com.iitism.concetto.auth.Refractor.BaseFragment
import com.iitism.concetto.databinding.FragmentChangePasswordBinding
import com.iitism.concetto.retrofit.Resource

class ChangePassword :BaseFragment<ChangeViewModel,FragmentChangePasswordBinding,ChangePassRespo>(){

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val intent=Intent(this.context, MainActivity::class.java)
        val progressBar=binding.changepasswordProgressBar

        viewModel.changeResponse.observe(viewLifecycleOwner,Observer {
            when(it){
                is Resource.Success->{
                    Log.d("ChangePassword", it.value.message.toString())
                    Toast.makeText(requireContext(),"Password Changed Successfully", Toast.LENGTH_LONG).show()
                    val visibility=if(progressBar.visibility==View.GONE) View.VISIBLE
                    else View.GONE
                    progressBar.visibility=visibility
                    startActivity(intent)
                    this.activity?.finish()
                }
                is Resource.Failure->{
                    Log.d("ChangePassword", it.toString())
                    Toast.makeText(requireContext(), it.errorCode.toString(), Toast.LENGTH_LONG).show()
                    val visibility=if(progressBar.visibility==View.GONE) View.VISIBLE
                    else View.GONE
                    progressBar.visibility=visibility
                }
            }
        })
        binding.changePassButton.setOnClickListener {
            val email=binding.changePasswordEmailEdit.text.toString()
            val oldPassword=binding.OldPasswordEdit.text.toString()
            val newPassword=binding.NewPasswordEdit.text.toString()
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