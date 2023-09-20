package com.example.letmeknow.Adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R
import com.iitism.concetto.ui.sponsors.Sponsor
import com.squareup.picasso.Picasso

class SponsorsAdapter(private var DataList: List<Sponsor>) :
    RecyclerView.Adapter<SponsorsAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val ItemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_sponsor, parent, false)
        return ViewHolder(ItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val currentData = DataList[position]
        if(currentData.img!=null){
            Picasso.get().load(currentData.img).into(holder.ivSponsorImg)
            //holder.ivSponsorImg.layoutParams.height = 200
        }

        holder.tvSponsortype.text = currentData.cat.toString()

        holder.ivSponsorImg.setOnClickListener{
            mListener.onItemClick(position)
        }
    }


    override fun getItemCount(): Int {
        return DataList.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val ivSponsorImg : ImageView = view.findViewById(R.id.sponsorImage)
        val tvSponsortype : TextView = view.findViewById(R.id.sponsorType)
    }

}


