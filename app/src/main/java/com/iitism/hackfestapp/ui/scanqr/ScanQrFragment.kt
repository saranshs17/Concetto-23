package com.iitism.hackfestapp.ui.scanqr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.integration.android.IntentIntegrator
import com.iitism.hackfestapp.databinding.FragmentScanqrBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import org.json.JSONException


class ScanQrFragment : Fragment() {


    private lateinit var viewModel: ScanQrViewModel
    private lateinit var binding: FragmentScanqrBinding
    private lateinit var qrScanIntegrator: IntentIntegrator
    private var ID : String? = null
    private val attendanceBaseURL = "https://hackfest-backend-3y92.onrender.com/attendance/"
    private val refreshmentOneBaseURL = "https://hackfest-backend-3y92.onrender.com/refreshment_counter/"
    private val refreshmentTwoBaseURL = "https://hackfest-backend-3y92.onrender.com/refreshment_counter_two/"

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

        binding.dismissButton.setOnClickListener{
            binding.refreshmentCard.visibility = View.GONE
            binding.notmarkedcard.visibility = View.VISIBLE

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
                    if(viewModel.isNetworkAvailable()){
                        if(result.equals("https://www.hackfestiitism.com/attendance")){
                            binding.loadingCard.loadingCard.visibility = View.VISIBLE
                            GlobalScope.launch(Dispatchers.IO) {
                                val response =viewModel.markAttendance(ID,attendanceBaseURL)
                                if(response.isSuccessful){
                                    GlobalScope.launch(Dispatchers.Main) {
                                        binding.loadingCard.loadingCard.visibility = View.GONE
                                        binding.markedcard.visibility = View.VISIBLE
                                        Toast.makeText(getContext(),"Attendance Marked"+response.body()?.message.toString() ,Toast.LENGTH_LONG).show()
                                        binding.markedcard.visibility = View.GONE
                                    }
                                }
                                else{
                                    GlobalScope.launch(Dispatchers.Main) {
                                        Toast.makeText(getContext(),"Too many members",Toast.LENGTH_LONG).show()
                                        binding.loadingCard.loadingCard.visibility = View.GONE
                                    }

                                }
                            }
                        }
                        else if(result.equals("https://www.hackfestiitism.com/refreshment")){
                            binding.loadingCard.loadingCard.visibility = View.VISIBLE
                            GlobalScope.launch(Dispatchers.IO) {
                                val response =viewModel.markAttendance(ID,refreshmentOneBaseURL)
                                if(response.isSuccessful){
                                    GlobalScope.launch(Dispatchers.Main) {
                                        binding.loadingCard.loadingCard.visibility = View.GONE
                                        binding.notmarkedcard.visibility = View.GONE
                                        binding.refreshmentCard.visibility = View.VISIBLE
                                        countdown()
                                    }
                                }
                                else{
                                    GlobalScope.launch(Dispatchers.Main) {
                                        Toast.makeText(getContext(),"The team limit has been reached.",Toast.LENGTH_LONG).show()
                                        binding.loadingCard.loadingCard.visibility = View.GONE
                                    }
                                }
                            }
                        }
                        else if (result.equals("https://www.hackfestiitism.com/refreshment_two")){
                            binding.loadingCard.loadingCard.visibility = View.VISIBLE
                            GlobalScope.launch(Dispatchers.IO) {
                                val response =viewModel.markAttendance(ID,refreshmentTwoBaseURL)
                                if(response.isSuccessful){
                                    GlobalScope.launch(Dispatchers.Main) {
                                        binding.loadingCard.loadingCard.visibility = View.GONE
                                        binding.notmarkedcard.visibility = View.GONE
                                        binding.refreshmentCard.visibility = View.VISIBLE
                                        countdown()
                                    }
                                }
                                else{
                                    GlobalScope.launch(Dispatchers.Main) {
                                        Toast.makeText(getContext(),"The team limit has been reached.",Toast.LENGTH_LONG).show()
                                        binding.loadingCard.loadingCard.visibility = View.GONE
                                    }
                                }
                            }
                        }
                        else{
                            GlobalScope.launch (Dispatchers.Main){
                                Toast.makeText(getContext(), "Scanned Wrong Qr",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    else{
                        GlobalScope.launch(Dispatchers.Main){
                            Toast.makeText(getContext(), "Network Error",Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun countdown(){
        object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var second = millisUntilFinished/1000
                binding.txtSecond.text = second.toString()
            }
            override fun onFinish() {
                binding.refreshmentCard.visibility = View.GONE
                binding.notmarkedcard.visibility = View.VISIBLE
            }
        }.start()

    }
}