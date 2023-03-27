package com.example.hackfestapp.ui.scanqr

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hackfestapp.R
import com.example.hackfestapp.databinding.FragmentScanqrBinding
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject

class ScanQrFragment : Fragment() {

    companion object {
        fun newInstance() = ScanQrFragment()
    }

    private lateinit var viewModel: ScanQrViewModel
    private lateinit var binding : FragmentScanqrBinding
    private lateinit var qrScanIntegrator : IntentIntegrator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanqrBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScanQrViewModel::class.java)
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
                    val obj = JSONObject(result.contents)
                    Toast.makeText(this.context,obj.getString("Name"),Toast.LENGTH_LONG).show()
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