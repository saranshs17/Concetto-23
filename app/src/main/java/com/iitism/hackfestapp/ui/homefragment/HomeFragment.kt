package com.iitism.hackfestapp.ui.homefragment

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore.Video
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iitism.hackfestapp.R
import com.iitism.hackfestapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding : FragmentHomeBinding
    private lateinit var videoView: VideoView
    private val path = "android.resource://com.iitism.hackfestapp/"+R.raw.bgm
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root
        return  view
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        videoView = binding.videoview
        videoView.setVideoPath(path)
        videoView.start()
        videoView.setOnCompletionListener {
            videoView.start()
        }

        countDownHackfestStart()
    }



    override fun onResume() {

        videoView.start()
        super.onResume()
    }

     fun countDownHackfestStart(){
        val handler = android.os.Handler()
        val runnable = object : java.lang.Runnable {
            override fun run() {
                handler.postDelayed(this, 1000)
                try {
                    val currentDate = Date()
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val futureDate: Date = dateFormat.parse("2023-4-7 19:00:00")
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
                        countDownHackfestEnd()
                        binding.textcounterdown.text = "Time Remaining"
                        binding.LinearLayout1.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        handler.postDelayed(runnable, 1 * 1000)

    }



    fun countDownHackfestEnd(){
        val handler = android.os.Handler()
        val runnable = object : java.lang.Runnable {
            override fun run() {
                handler.postDelayed(this, 1000)
                try {
                    val currentDate = Date()
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val futureDate: Date = dateFormat.parse("2023-4-3 07:00:00")
                    if (!currentDate.after(futureDate)) {
                        var diff: Long = (futureDate.getTime()
                                - currentDate.getTime())
                        val hours = diff / (60 * 60 * 1000)
                        diff -= hours * (60 * 60 * 1000)
                        val minutes = diff / (60 * 1000)
                        diff -= minutes * (60 * 1000)
                        val seconds = diff / 1000
                        binding.txtHour.setText("" + String.format("%02d", hours))
                        binding.txtMinute.setText("" + String.format("%02d", minutes))
                        binding.txtSecond.setText(("" + String.format("%02d", seconds)))
                    }
                    else {
                        binding.textcounterdown.text = "Thank's for Participating"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        handler.postDelayed(runnable, 1 * 1000)
    }





}