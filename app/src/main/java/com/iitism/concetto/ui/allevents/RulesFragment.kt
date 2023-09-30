package com.iitism.concetto.ui.allevents

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R
import com.iitism.concetto.ui.clubevents.Club_dataclass

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RulesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RulesFragment : Fragment(R.layout.fragment_rules) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var rulesArrayList : ArrayList<String>
    private lateinit var arrayList: List<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        recyclerView = view.findViewById(R.id.rv_rules)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)


     //  List                               get the rules list from Club_dataclass
      // rulesArrayList =                   put the list in rules array list
        recyclerView.adapter = RulesAdapter(rulesArrayList)
    }



}