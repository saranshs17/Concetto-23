package com.iitism.hackfestapp.ui.scanqr

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.iitism.hackfestapp.databinding.FragmentScanqrBinding
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException

class ScanQrFragment : Fragment() {


    private lateinit var viewModel: ScanQrViewModel
    private lateinit var binding: FragmentScanqrBinding
    private lateinit var qrScanIntegrator: IntentIntegrator
    private var ID : String? = null
    private val attendanceBaseURL = "https://hackfest-backend-3y92.onrender.com/attendance/"
    private val refreshmentBaseURL = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanqrBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,ScanQrViewModelFactory(requireContext(),this.activity)).get(ScanQrViewModel::class.java)
        qrScanIntegrator = IntentIntegrator.forSupportFragment(this)

        val sharedPreferences = this.activity?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        ID = sharedPreferences?.getString("teamId","")

        binding.scanQrButton.setOnClickListener {
            viewModel.setupScanner(qrScanIntegrator)
            viewModel.performAction(qrScanIntegrator)
        }



    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(activity, "No Result Found", Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    val result = result.contents.toString()
                    Log.d("Scan Qr", result)
                    if(result.equals("https://www.hackfestiitism.com/attendance")){
                        binding.loadingCard.loadingCard.visibility = View.VISIBLE
                        GlobalScope.launch(Dispatchers.IO) {
                            val response =viewModel.markAttendance(ID,attendanceBaseURL)
                            if(response.isSuccessful){
                                GlobalScope.launch(Dispatchers.Main) {
                                    Toast.makeText(context,"Attendance Marked",Toast.LENGTH_LONG).show()
                                    binding.markedcard.visibility = View.VISIBLE
                                    binding.loadingCard.loadingCard.visibility = View.GONE
                                    binding.markedcard.visibility = View.GONE
                                }
                            }
                        }
                    }
                    else if(result.equals("https://www.hackfestiitism.com/refreshment")){
                        binding.loadingCard.loadingCard.visibility = View.VISIBLE
                        GlobalScope.launch(Dispatchers.IO) {
                            val response =viewModel.markAttendance(ID,refreshmentBaseURL)
                            if(response.isSuccessful){
                                GlobalScope.launch(Dispatchers.Main) {
                                    Toast.makeText(context,"Refreshment Marked",Toast.LENGTH_LONG).show()
                                    binding.markedcard.visibility = View.VISIBLE
                                    binding.loadingCard.loadingCard.visibility = View.GONE
                                    binding.markedcard.visibility = View.GONE
                                }
                            }
                        }
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
//                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}