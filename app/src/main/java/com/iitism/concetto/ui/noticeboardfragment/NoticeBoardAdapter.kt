package com.iitism.concetto.ui.noticeboardfragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.databinding.NoticeBoardCardViewBinding

class NoticeBoardAdapter : RecyclerView.Adapter<NoticeBoardAdapter.ViewHolder>() {

    private var list = mutableListOf<NoticeBoardModel>()

    fun setNotices ( list : MutableLiveData<List<NoticeBoardModel>>)
    {
        this.list = list.value!!.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NoticeBoardCardViewBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.setText(list.get(position).title)
        holder.binding.description.setText(list.get(position).description)
    }

    override fun getItemCount(): Int {
       Log.d("hhhh" , list.size.toString())
       return list.size
    }

    class ViewHolder (val binding : NoticeBoardCardViewBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}