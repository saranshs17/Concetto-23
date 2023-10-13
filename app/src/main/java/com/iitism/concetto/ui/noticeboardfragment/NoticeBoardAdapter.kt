package com.iitism.concetto.ui.noticeboardfragment

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.databinding.NoticeBoardCardViewBinding

class NoticeBoardAdapter : RecyclerView.Adapter<NoticeBoardAdapter.ViewHolder>() {

    private var list = mutableListOf<NoticeBoardModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    fun setNotices (list : MutableLiveData<List<NoticeBoardModel>>)
    {
        this.list = list.value!!.toMutableList()
        this.list.apply {
            this.removeIf {
                it.message==null||it.title==null
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NoticeBoardCardViewBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val position2 = list.size-1-position
        if(list.get(position2).title!=null&&list.get(position2).message!=null){
            holder.binding.title.setText(list.get(position2).title.toString())
            holder.binding.description.setText(list.get(position2).message.toString())
        }
    }

    override fun getItemCount(): Int {
       Log.d("hhhh" , list.size.toString())
       return list.size
    }

    class ViewHolder (val binding : NoticeBoardCardViewBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}