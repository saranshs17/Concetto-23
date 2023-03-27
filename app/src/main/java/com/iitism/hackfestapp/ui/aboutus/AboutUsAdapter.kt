package com.iitism.hackfestapp.ui.aboutus

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iitism.hackfestapp.R
import com.iitism.hackfestapp.databinding.AboutUsCardViewBinding

class AboutUsAdapter : RecyclerView.Adapter<AboutUsAdapter.AboutUsViewHolder>() {

    private lateinit var context: Context
    var organizerList = mutableListOf<AboutUsModel>()
    @SuppressLint("NotifyDataSetChanged")
    fun setorganizerList(organizerList : MutableLiveData<List<AboutUsModel>>){
        this.organizerList = organizerList.value!!.toMutableList()
        Log.d("tag","organizerlist --> ${this.organizerList}")
        notifyDataSetChanged()
        Log.d("tag","notified the data set changed")
    }

    fun getContext(){return }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutUsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AboutUsCardViewBinding.inflate(layoutInflater,parent,false)
        context = binding.root.context
        return AboutUsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AboutUsViewHolder, position: Int) {
        val organizer = organizerList[position]
        holder.binding.orgName.text = organizer.name
        holder.binding.orgPosition.text =organizer.position
        Glide.with(holder.binding.root.context)
            .load(organizer.image)
            .placeholder(R.drawable.person)
            .centerCrop()
            .circleCrop()
            .into(holder.binding.orgImage)
        if(organizer.linkedin_url != ""){
            holder.binding.orgLinkedin.setOnClickListener {
                val url = organizer.linkedin_url
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(it.context,intent,null)
            }
        }else{
            holder.binding.orgLinkedin.setOnClickListener {
                Log.d("tag","Not available at Linkedin")
            }
        }
        if(organizer.insta_url != ""){
            holder.binding.orgInsta.setOnClickListener {
                val url = organizer.insta_url
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(it.context,intent,null)
            }
        }
        else{
            holder.binding.orgInsta.setOnClickListener {
                Log.d("tag","Not available at Insta")
            }
        }

    }

    override fun getItemCount(): Int {
        return organizerList.size
    }
    class AboutUsViewHolder(val binding : AboutUsCardViewBinding) : RecyclerView.ViewHolder(binding.root){

    }

}

