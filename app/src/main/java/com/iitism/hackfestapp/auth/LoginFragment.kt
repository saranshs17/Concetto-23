package com.iitism.hackfestapp.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.iitism.hackfestapp.MainActivity
import com.iitism.hackfestapp.R
import com.iitism.hackfestapp.auth.Refractor.LoginRepository
import com.iitism.hackfestapp.auth.Refractor.LoginViewModel
import com.iitism.hackfestapp.auth.Refractor.BaseFragment
import com.iitism.hackfestapp.changePassword.ChangePassword
import com.iitism.hackfestapp.databinding.FragmentLoginBinding
import com.iitism.hackfestapp.retrofit.AuthApiLogin
import com.iitism.hackfestapp.retrofit.Resource

class loginFragment : BaseFragment<LoginViewModel,FragmentLoginBinding, LoginRepository>() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val intent=Intent(context,MainActivity::class.java)
        val sharedPreferences=this.activity?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor=sharedPreferences?.edit()
        val teamId=sharedPreferences?.getString("teamId","")
        if(teamId!=""){
            startActivity(intent)
        }

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success->{
                    Log.d("Login", it.value.data.toString())
//                    val user= User(it.value.data.Player_Email,it.value.data.Team_Name,it.value.data.Player_Mobile,it.value.data.problem_statement_and_solution,it.value.data.Player_Organisation)
                    editor?.apply {
                        putString("email",it.value.data.Player_Email.toString())
                        putString("teamId",it.value.data.Team_Id.toString())
                        apply()
                    }
                    intent.putExtra("playerEmail",it.value.data.Player_Email)
                    intent.putExtra("teamName",it.value.data.Team_Name)
                    intent.putExtra("playerMobile",it.value.data.Player_Mobile)
                    intent.putExtra("problemStatement",it.value.data.problem_statement_and_solution)
                    intent.putExtra("playerOrgainzation",it.value.data.Player_Organisation)
                    intent.putExtra("teamId",it.value.data.Team_Id)
                    Toast.makeText(requireContext(),"Welcome Back to ${it.value.data.Team_Name}",Toast.LENGTH_LONG).show()
                    startActivity(intent)
                }
                is Resource.Failure->{
                    Toast.makeText(requireContext(), it.errorBody.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.DontSignupTextView.setOnClickListener{
            val fragmentManager=parentFragment?.parentFragmentManager
            val fragmentTransaction=fragmentManager?.beginTransaction()
            val nextFragment=ChangePassword()
            fragmentTransaction?.replace(R.id.fragmentContainerView,nextFragment)?.commit()
//

        }
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