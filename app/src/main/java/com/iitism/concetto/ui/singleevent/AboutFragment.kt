package com.iitism.concetto.ui.singleevent

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.iitism.concetto.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AboutFragment(
    var arrayList: ArrayList<SingleEventModelItem>?
) : Fragment(R.layout.fragment_about) {


    private lateinit var tvEventName : TextView
    private lateinit var tvFee : TextView
    private lateinit var tvMode : TextView
    private lateinit var tvOrganizer : TextView
    private lateinit var tvEventDesc : TextView
    private lateinit var tvOrgDesc : TextView
    private lateinit var tvPrizes : TextView
    private lateinit var imageView: ImageView



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("AboutList",arrayList.toString())
        tvEventName = view.findViewById(R.id.event_name)
        tvFee = view.findViewById(R.id.eventFee)
        tvMode= view.findViewById(R.id.eventMode)
        tvOrganizer = view.findViewById(R.id.event_organizer)
        tvOrgDesc = view.findViewById(R.id.event_organizerDescription)
        tvPrizes = view.findViewById(R.id.event_prizes)
        tvEventDesc = view.findViewById(R.id.event_description)
        imageView = view.findViewById(R.id.event_image)
        run()
    }


    private fun run(){

        tvEventName.text=arrayList?.get(0)?.name ?: "Loading"
        tvFee.text=arrayList?.get(0)?.fees?.toString() ?: "Loading"
        tvMode.text=arrayList?.get(0)?.mode ?: "Loading"
        tvOrganizer.text=arrayList?.get(0)?.organizer ?: "Loading"
        tvEventDesc.text=arrayList?.get(0)?.descriptionEvent ?: "Loading"
        tvOrgDesc.text=arrayList?.get(0)?.descriptionOrganizer ?: "Loading"
        tvPrizes.text=arrayList?.get(0)?.prizes ?: "Loading"

        activity?.let {
            Glide.with(it)
                .load(arrayList?.get(0)?.posterMobile) // Replace with the URL or resource for the image
                .placeholder(R.drawable.concetto_full_logo) // Optional placeholder drawable
                .error(R.drawable.concetto_full_logo) // Optional error drawable
                .centerCrop()
                .into(imageView)
        }

    }


}