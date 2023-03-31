package com.iitism.hackfestapp.ui.homefragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iitism.hackfestapp.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root
        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        countDownStart()
    }

    fun countDownStart(){
        val handler = android.os.Handler()
        val runnable = object : java.lang.Runnable {
            override fun run() {
                handler.postDelayed(this, 1000)
                try {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
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
//                        binding.textcounterdown.text = "Time Remaining"
//                        startTimer(129600)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        handler.postDelayed(runnable, 1 * 1000)

    }

    private fun startTimer(durationInSeconds: Long) {
        val countDownTimer = object : CountDownTimer(durationInSeconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                val minutes =
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                        hours
                    )
                val seconds =
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        minutes
                    )
                val timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                binding.txtHour.setText("" + String.format("%02d", hours))
                binding.txtMinute.setText("" + String.format("%02d", minutes))
                binding.txtSecond.setText(("" + String.format("%02d", seconds)))
            }

            override fun onFinish() {
                binding.LinearLayout.visibility = View.GONE
                binding.finishedId.visibility = View.VISIBLE
            }
        }
    }
}