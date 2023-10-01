package com.iitism.concetto.ui.singleevent

import android.content.Intent
import android.graphics.PointF
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R

class RulesFragment(
    val RulesList : List<String>?,
    val Pdflink : String?
) : Fragment(R.layout.fragment_rules) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var rulesArrayList : ArrayList<String>
    private lateinit var pdfLink : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = view.findViewById(R.id.rvRules)
        pdfLink = view.findViewById(R.id.pdfLink)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

//        arrayList = ViewerActivity().getRules()


        //  List                               get the rules list from Club_dataclass
      rulesArrayList =   ArrayList(RulesList)
        if(rulesArrayList.isNotEmpty())
            Log.i("Rules",rulesArrayList.toString())

        // put the list in rules array list
        recyclerView.adapter = RulesAdapter(rulesArrayList)


        pdfLink.setOnClickListener {
            if (Pdflink != null) {
                val url = Pdflink
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                if (intent.resolveActivity(requireContext().packageManager) != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "No web browser available", Toast.LENGTH_SHORT).show()
                }
            }
            else
                Toast.makeText(context, "No PDF Link available", Toast.LENGTH_SHORT).show()
        }
    }



}