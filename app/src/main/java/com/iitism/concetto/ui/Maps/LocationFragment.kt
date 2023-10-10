package com.iitism.concetto.ui.Maps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R

class LocationFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LocationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =  inflater.inflate(R.layout.fragment_location, container, false)

        recyclerView = rootView.findViewById(R.id.rv_location)
        recyclerView.layoutManager = LinearLayoutManager(context)


        val locationList = listOf(
            LocationDataModel(1,"Students Activity Center", 23.817602991767018, 86.43754362173524),
            LocationDataModel(2,"Heritage Building", 23.81440325360859, 86.44115923927656),
            LocationDataModel(3,"Central Library", 23.816593005598243, 86.44243350117469),
            LocationDataModel(4,"Electric Substation", 23.814384533800013, 86.43874276877608),
            LocationDataModel(5,"Old Library", 23.81554269568486, 86.44127475919854),
            LocationDataModel(6,"National Cadet Corps", 23.812020476116697, 86.43907138157573),
            LocationDataModel(7,"CHW Office", 23.81591567568406, 86.44075977505234),
            LocationDataModel(8,"Ism Upper Ground", 23.813402947637208, 86.4428197117042),
            LocationDataModel(9,"New Lecture Complex", 23.816462746604728, 86.43997638408928),
            LocationDataModel(10,"Jasper Hostel", 23.816709534948973, 86.44084657883755),
            LocationDataModel(11,"Admin Block", 23.81425755245915, 86.44238682361436),
            LocationDataModel(12,"IsmSports Office", 23.81322030671901, 86.43993656695105)
            )

        adapter = LocationAdapter(requireContext(),locationList)
        recyclerView.adapter = adapter
         return rootView
    }

}