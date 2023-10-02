package com.iitism.concetto.ui.sponsors

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeknow.Adapters.SponsorsAdapter
import com.iitism.concetto.R


class SponsorsFragment : Fragment(R.layout.fragment_sponsors) {

    private lateinit var vm: viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sponsors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val SponsorsRecyclerView: RecyclerView = view.findViewById(R.id.recycler_view_sponsors)
        SponsorsRecyclerView.layoutManager = LinearLayoutManager(context)
        SponsorsRecyclerView.setHasFixedSize(true)

        vm = viewmodel(requireContext())
        vm.getSponsorList()

        val itemAdapter = SponsorsAdapter(vm.sponsorList)
        SponsorsRecyclerView.adapter = itemAdapter

        itemAdapter.setOnItemClickListener(object: SponsorsAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                try {
                    val url = vm.sponsorList[position].link
                    val i = Intent()
                    i.setPackage("com.android.chrome")
                    i.action = Intent.ACTION_VIEW
                    i.data = Uri.parse(url)
                    startActivity(i)
                } catch (e: ActivityNotFoundException){
                    Toast.makeText(context,"Chrome not Installed",Toast.LENGTH_SHORT).show()
                }

            }

        })


    }


}
