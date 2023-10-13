package com.iitism.concetto.ui.noticeboardfragment

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.iitism.concetto.MainActivity
import com.iitism.concetto.R
import com.iitism.concetto.databinding.FragmentAddNoticeBinding
import com.iitism.concetto.ui.fcm_service_package.fcm_api_service_package.NotificationService
import com.iitism.concetto.ui.fcm_service_package.token_api_service_package.ApiService
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
//        Toast.makeText(requireContext(),"Welcome Admin",Toast.LENGTH_SHORT).show()
        binding.btnSendNotice.setOnClickListener {
            val title = binding.tvTitle.text.toString()
            val message = binding.tvMessage.text.toString()

            if (title.isEmpty()) binding.tvTitle.error = "title Can't be empty"
            if (message.isEmpty()) binding.tvMessage.error = "Message can't be empty"
            if (title.isNotEmpty() && message.isNotEmpty()) {
                val databody = AddNoticeDataBody(title, message)
                viewModel.addNoticeService(databody)
                Toast.makeText(
                    binding.root.context,
                    "Notice Sent Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                GlobalScope.launch {
                    tokenList =
                        ApiService().getRegisteredTokenListService(requireContext())
                    delay(2500)
                    Log.d("Devices Token List=>>>", tokenList.toString())
                    delay(2500)
                    NotificationService().sendNotification(tokenList, message, title)
                }

                binding.tvTitle.text?.clear()
                binding.tvMessage.text?.clear()
                binding.btnSendNotice.visibility = View.GONE
                Handler().postDelayed({
                    binding.btnSendNotice.visibility = View.VISIBLE
                }, 2000)
            }
        }
//        val dialog = AlertDialog.Builder(requireContext())
//            .setTitle("ONLY ADMIN CAN ACCESS")
//            .setMessage("Please enter the password to send the notification:")
//            .setView(R.layout.dialog)
//            .setCancelable(false)
//            .setPositiveButton("CONFIRM"){ dialogInterface, _ ->
//
//                val inputString = (dialogInterface as AlertDialog).findViewById<EditText>(R.id.passDiag)!!.text.toString()
//                dialogInterface.setCancelable(false)
//                dialogInterface.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                // Compare the input string to the backend string.
//                if (inputString.trim() == "ElvishBhai") {
//                    // Execute the further steps.
//
//
//                } else {
//                    // Display an error message indicating that the user does not have admin access.
//                    Toast.makeText(requireContext(), "You do not have admin access.", Toast.LENGTH_LONG).show()
//                    val intent = Intent(requireContext(),MainActivity::class.java)
//                    startActivity(intent)
//                }
//            }
//            .setNegativeButton("Cancel") { dialogInterface, _ ->
//                dialogInterface.dismiss()
//                val intent = Intent(requireContext(),MainActivity::class.java)
//                startActivity(intent)
//            }
//            .create()
//
//        dialog.show()

        }
}



