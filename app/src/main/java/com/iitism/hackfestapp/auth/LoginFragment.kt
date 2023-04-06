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
import androidx.preference.PreferenceManager
import com.iitism.hackfestapp.MainActivity
import com.iitism.hackfestapp.R
import com.iitism.hackfestapp.auth.Refractor.LoginRepository
import com.iitism.hackfestapp.auth.Refractor.LoginViewModel
import com.iitism.hackfestapp.auth.Refractor.BaseFragment
import com.iitism.hackfestapp.changePassword.ChangePassword
import com.iitism.hackfestapp.databinding.FragmentLoginBinding
import com.iitism.hackfestapp.retrofit.AuthApiLogin
import com.iitism.hackfestapp.retrofit.Resource
import java.util.prefs.Preferences

class loginFragment : BaseFragment<LoginViewModel,FragmentLoginBinding, LoginRepository>() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val intent=Intent(context,MainActivity::class.java)
        val sharedPreferences=this.activity?.getSharedPreferences("myPref",Context.MODE_PRIVATE)
        val editor=sharedPreferences?.edit()
        val teamId=sharedPreferences?.getString("teamId","")
        if(teamId!=""){
            startActivity(intent)
            this.activity?.finish()
        }
        val progressBar=binding.loadingCard.loadingCard

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer { it ->
            Log.d("hhhhhh" , it.toString());
            when(it){
                is Resource.Success->{
                    Log.d("Login", it.value.data.toString())
//                    val user= User(it.value.data.Player_Email,it.value.data.Team_Name,it.value.data.Player_Mobile,it.value.data.problem_statement_and_solution,it.value.data.Player_Organisation)
                    editor?.apply {
                        putString("teamId",it.value.data.Team_Id.toString())
                        putString("email",it.value.data.Player_Email.toString())
                        putString("teamName",it.value.data.Team_Name)
                        putString("playerName",it.value.data.Player_Name)
                        putString("playerType",it.value.data.Player_Type)
                        putLong("playerMobile",it.value.data.Player_Mobile)
                        putString("problemStatement",it.value.data.problem_statement_and_solution)
                        putString("playerOrganization",it.value.data.Player_Organisation)
                        apply()
                    }
                    val visibility=if(progressBar.visibility==View.GONE) View.VISIBLE
                    else View.GONE
                    progressBar.visibility=visibility
                    Toast.makeText(requireContext(),"Welcome Back to ${it.value.data.Team_Name}",Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    this.activity?.finish()
                }
                is Resource.Failure->{
                    progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Please Try Again!", Toast.LENGTH_SHORT).show()
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
            val teamName=binding.teamNameEditTextLogin.text.toString()
            val password=binding.passwordLogin.text.toString()
            val visibility=if(progressBar.visibility==View.GONE) View.VISIBLE
            else View.GONE
            progressBar.visibility=visibility
            if(teamName.isEmpty()){
                if(progressBar.visibility == View.VISIBLE) {
                    progressBar.visibility = View.GONE
                }
                Toast.makeText(context,"Player Email is required",Toast.LENGTH_LONG).show()
            }else if(password.isEmpty()){
                if(progressBar.visibility == View.VISIBLE) {
                    progressBar.visibility = View.GONE
                }
                Toast.makeText(context,"Password is required",Toast.LENGTH_LONG).show()
            }else{
                if(viewModel.isNetworkAvailable()){
                    viewModel.login(teamName,password)
                }
                else{
                    Toast.makeText(context, "Network Error",Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
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