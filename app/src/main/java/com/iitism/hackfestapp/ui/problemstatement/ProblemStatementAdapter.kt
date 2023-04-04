package com.iitism.hackfestapp.ui.problemstatement

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iitism.hackfestapp.R
import com.iitism.hackfestapp.databinding.ProblemStatementCardViewBinding

class ProblemStatementAdapter : RecyclerView.Adapter<ProblemStatementAdapter.ViewHolder>() {

    private var list = mutableListOf<ProblemStatementModel>()

    fun setProblems ( list : MutableLiveData<List<ProblemStatementModel>>)
    {
        this.list = list.value!!.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProblemStatementCardViewBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.orgName.setText(list.get(position).name)
        holder.binding.orgLink.setText(list.get(position).problem_url)

        Glide.with(holder.binding.root.context)
            .load(list.get(position).image_logo)
            .placeholder(R.drawable.person)
            .centerCrop()
            .circleCrop()
            .into(holder.binding.orgImage)

        if(list.get(position).problem_url != ""){
            holder.binding.orgLink.setOnClickListener {
                val url = list.get(position).problem_url
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                ContextCompat.startActivity(it.context, intent, null)
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("hhhh" , list.size.toString())
        return list.size
    }

    class ViewHolder (val binding : ProblemStatementCardViewBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}