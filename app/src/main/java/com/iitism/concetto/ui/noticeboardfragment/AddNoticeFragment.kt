package com.iitism.concetto.ui.noticeboardfragment

import android.app.ProgressDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.iitism.concetto.R
import com.iitism.concetto.databinding.FragmentAddNoticeBinding
import com.iitism.concetto.ui.fcm_service_package.fcm_api_service_package.NotificationService
import com.iitism.concetto.ui.fcm_service_package.token_api_service_package.ApiService
import com.iitism.concetto.ui.homefragment.HomeFragment
import com.iitism.concetto.ui.noticeboardfragment.retrofit.AddNoticeDataBody
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddNoticeFragment : Fragment() {

    companion object {
        fun newInstance() = AddNoticeFragment()
    }

    private lateinit var viewModel: AddNoticeViewModel
    private lateinit var binding : FragmentAddNoticeBinding
    private lateinit var tokenList:ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddNoticeBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddNoticeViewModel::class.java)


        binding.btnSendNotice.setOnClickListener {
            val title=binding.tvTitle.text.toString()
            val message=binding.tvMessage.text.toString()
            if(title.isEmpty()) binding.tvTitle.error= "title Can't be empty"
            if(message.isEmpty()) binding.tvMessage.error = "Message can't be empty"
            if(title.isNotEmpty() && message.isNotEmpty()){
                val databody=AddNoticeDataBody(title,message)
                viewModel.addNoticeService(databody)
                Toast.makeText(binding.root.context,"Notice Sent Successfully",Toast.LENGTH_SHORT).show()
                GlobalScope.launch {
                    tokenList = ApiService().getRegisteredTokenListService(requireContext())
                    delay(2500)
                    Log.d("Devices Token List=>>>", tokenList.toString())
                    delay(2500)
                    NotificationService().sendNotification(tokenList,message,title)
                }
                binding.tvTitle.text?.clear()
                binding.tvMessage.text?.clear()
                binding.btnSendNotice.visibility = View.GONE
                Handler().postDelayed({
                    binding.btnSendNotice.visibility= View.VISIBLE
                }, 2000)
            }



        }
    }

}