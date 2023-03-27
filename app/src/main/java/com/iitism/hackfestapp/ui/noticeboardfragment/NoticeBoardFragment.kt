package com.iitism.hackfestapp.ui.noticeboardfragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iitism.hackfestapp.R

class NoticeBoardFragment : Fragment() {

    companion object {
        fun newInstance() = NoticeBoardFragment()
    }

    private lateinit var viewModel: NoticeBoardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notice_board, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoticeBoardViewModel::class.java)
        // TODO: Use the ViewModel
    }

}