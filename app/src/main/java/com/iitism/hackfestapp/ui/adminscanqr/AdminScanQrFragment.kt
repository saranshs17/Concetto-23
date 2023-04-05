package com.iitism.hackfestapp.ui.adminscanqr

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.iitism.hackfestapp.R
import com.iitism.hackfestapp.databinding.FragmentAdminScanQrBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException

class AdminScanQrFragment : Fragment() {

    companion object {
        fun newInstance() = AdminScanQrFragment()
    }

    private lateinit var viewModel: AdminScanQrViewModel
    private lateinit var binding : FragmentAdminScanQrBinding
    private lateinit var qrScanIntegrator : IntentIntegrator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminScanQrBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,AdminScanQrViewModelFactory(requireContext()))[(AdminScanQrViewModel::class.java)]
        qrScanIntegrator = IntentIntegrator.forSupportFragment(this)

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
                    val url = result.contents.toString()
                    Log.d("Scan Qr", url)
//                    viewModel.markInOut(url)
//                    viewModel.setupScanner(qrScanIntegrator)
//                    viewModel.performAction(qrScanIntegrator)
                    GlobalScope.launch(Dispatchers.IO) {
                        if(viewModel.isNetworkAvailable()){
                            val response = viewModel.markInOut(scannedtext = url)
                            if(response.isSuccessful){
                                Log.d("AdminScanQR","Gate Pass : ${response.body()?.message}")
                                GlobalScope.launch (Dispatchers.Main){
                                    Toast.makeText(getContext(), "Gate Pass : ${response.body()?.message}\"", Toast.LENGTH_SHORT).show()
                                }
                                viewModel.setupScanner(qrScanIntegrator)
                                viewModel.performAction(qrScanIntegrator)
                            }
                            else{
                                GlobalScope.launch(Dispatchers.Main) {
                                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show()
                                }
                                viewModel.setupScanner(qrScanIntegrator)
                                viewModel.performAction(qrScanIntegrator)
                            }
                        }
                        else{
                            GlobalScope.launch(Dispatchers.Main) {
                                Toast.makeText(getContext(),"No Network",Toast.LENGTH_SHORT).show()
                            }
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




}