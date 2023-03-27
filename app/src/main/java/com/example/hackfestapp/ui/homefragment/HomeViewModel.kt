package com.example.hackfestapp.ui.homefragment

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.example.hackfestapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.Runnable
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Handler

class HomeViewModel : ViewModel() {

    fun countDownStart(binding: FragmentHomeBinding){
        val handler = android.os.Handler()
        val runnable = object : java.lang.Runnable {
            override fun run() {
                handler.postDelayed(this, 1000)
                try {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                    // Please here set your event date//YYYY-MM-DD
                    val futureDate: Date = dateFormat.parse("2023-4-7")
                    val currentDate = Date()
                    if (!currentDate.after(futureDate)) {
                        var diff: Long = (futureDate.getTime()
                                - currentDate.getTime())
                        val days = diff / (24 * 60 * 60 * 1000)
                        diff -= days * (24 * 60 * 60 * 1000)
                        val hours = diff / (60 * 60 * 1000)
                        diff -= hours * (60 * 60 * 1000)
                        val minutes = diff / (60 * 1000)
                        diff -= minutes * (60 * 1000)
                        val seconds = diff / 1000
                        binding.txtDay.setText("" + String.format("%02d", days))
                        binding.txtHour.setText("" + String.format("%02d", hours))
                        binding.txtMinute.setText("" + String.format("%02d", minutes))
                        binding.txtSecond.setText(("" + String.format("%02d", seconds)))
                    }
                    else {
                        binding.tveventStart.setVisibility(View.VISIBLE)
                        binding.tveventStart.setText("The event started!")
                        textViewGone(binding)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        handler.postDelayed(runnable, 1 * 1000)

    }

    fun textViewGone(binding: FragmentHomeBinding){
        binding.LinearLayout1.setVisibility(View.GONE)
        binding.LinearLayout2.setVisibility(View.GONE)
        binding.LinearLayout3.setVisibility(View.GONE)
        binding.LinearLayout4.setVisibility(View.GONE)
        binding.LinearLayout1.setVisibility(View.GONE)
    }


}