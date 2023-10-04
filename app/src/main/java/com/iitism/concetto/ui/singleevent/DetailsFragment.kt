package com.iitism.concetto.ui.singleevent

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R


class DetailsFragment(
    val arrayList : ArrayList<SingleEventModelItem>?
): Fragment(R.layout.fragment_details) {

    private lateinit var recyclerViewStages: RecyclerView
    private lateinit var tvProblemStatement : TextView
    private lateinit var recyclerViewContacts: RecyclerView
    private lateinit var recyclerViewExtraDetails: RecyclerView
    private var extraDetailsArrayPair : ArrayList<Pair<ArrayList<String>,String>> = arrayListOf()
    private  var StagesArrayPair: ArrayList<Pair<String,Pair<String,String>>> = arrayListOf()
    private  var ContactsArrayPair : ArrayList<Pair<String,String>> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("details array",arrayList.toString())

        recyclerViewStages= view.findViewById(R.id.rv_stages)
        tvProblemStatement= view.findViewById(R.id.tv_problemstatement)

        recyclerViewContacts= view.findViewById(R.id.rv_contact)

        recyclerViewExtraDetails = view.findViewById(R.id.rv_extraDetails)
        tvProblemStatement.text= arrayList?.get(0)?.problemStatements ?: "Nothing"

        recyclerViewStages.layoutManager= LinearLayoutManager(activity)
        recyclerViewContacts.layoutManager = LinearLayoutManager(activity)
        recyclerViewExtraDetails.layoutManager = LinearLayoutManager(activity)
        recyclerViewContacts.setHasFixedSize(true)
        recyclerViewStages.setHasFixedSize(true)
        recyclerViewExtraDetails.setHasFixedSize(true)

        Log.i("array",arrayList?.get(0)?.contacts!!.toString())

        for(i in arrayList?.get(0)?.contacts!!) {
            ContactsArrayPair.add(Pair(i.name,i.phoneNumber))
        }

        if(ContactsArrayPair.isEmpty()) ContactsArrayPair.add(Pair("Nothing","Nothing"))

        for(j in arrayList?.get(0)?.stages!!){
           StagesArrayPair.add(Pair(j.description,Pair(j.timing, j.venue)))
        }
        Log.i("ContactsList",ContactsArrayPair.toString())

        if(StagesArrayPair.isEmpty()){
            StagesArrayPair.add(Pair("Nothing",Pair("Nothing","Nothing")))
        }

        for (k in arrayList?.get(0)?.extraDetails!!){
            extraDetailsArrayPair.add(Pair(k.value,k.key) as Pair<ArrayList<String>, String>)
        }
        recyclerViewContacts.adapter = ContactsAdapter(ContactsArrayPair)
        recyclerViewStages.adapter = StagesAdapter(StagesArrayPair)
        recyclerViewExtraDetails.adapter = extraDetailsAdapter(extraDetailsArrayPair)
        }
    }

