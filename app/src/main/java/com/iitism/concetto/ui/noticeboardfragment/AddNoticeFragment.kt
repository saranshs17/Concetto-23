package com.iitism.concetto.ui.noticeboardfragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iitism.concetto.R

class AddNoticeFragment : Fragment() {

    companion object {
        fun newInstance() = AddNoticeFragment()
    }

    private lateinit var viewModel: AddNoticeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_notice, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddNoticeViewModel::class.java)
        // TODO: Use the ViewModel

    }

}