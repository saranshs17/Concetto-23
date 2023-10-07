package com.iitism.concetto.ui.noticeboardfragment

import android.app.ProgressDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.iitism.concetto.R
import com.iitism.concetto.databinding.FragmentAddNoticeBinding
import com.iitism.concetto.ui.noticeboardfragment.retrofit.AddNoticeDataBody

class AddNoticeFragment : Fragment() {

    companion object {
        fun newInstance() = AddNoticeFragment()
    }

    private lateinit var viewModel: AddNoticeViewModel
    private lateinit var binding : FragmentAddNoticeBinding

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
        binding.root.findViewById<Button>(R.id.btn_sendNotice).setOnClickListener {
            val title=binding.tvTitle.text.toString()
            val message=binding.tvMessage.text.toString()
            Toast.makeText(binding.root.context,title+message,Toast.LENGTH_SHORT).show()
            val databody=AddNoticeDataBody(title,message)
            viewModel.addNoticeService(databody)
        }
    }

}