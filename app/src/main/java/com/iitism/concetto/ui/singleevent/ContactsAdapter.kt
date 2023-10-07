package com.iitism.concetto.ui.singleevent

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R

class ContactsAdapter(private val contactsArrayPair : ArrayList<Pair<String,String>>) : RecyclerView.Adapter<ContactsAdapter.MyViewHolder>(){


    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val contactView = LayoutInflater.from(parent.context).inflate(
            R.layout.contact_details,parent,false
        )
        return MyViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return  contactsArrayPair.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = contactsArrayPair[position]
        holder.contactName.text= currentItem.first
        holder.contactNumber.text= currentItem.second

        holder.contactNumber.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:+91 ${currentItem.second}"))
            startActivity(context,intent, Bundle())
        }
    }

    class MyViewHolder(View : View) : RecyclerView.ViewHolder(View){
        val contactName : TextView = View.findViewById(R.id.tv_contactName)
        val contactNumber : TextView = View.findViewById(R.id.tv_ContactNumber)
    }
}