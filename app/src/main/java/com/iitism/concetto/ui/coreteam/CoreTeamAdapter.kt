package com.iitism.concetto.ui.coreteam

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeknow.Adapters.SponsorsAdapter
import com.iitism.concetto.R
import com.iitism.concetto.databinding.FragmentCoreTeamBinding
import com.squareup.picasso.Picasso
import java.util.zip.Inflater

class CoreTeamAdapter(private val dataList:List<CoreTeamDataModel>) :
    RecyclerView.Adapter<CoreTeamAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.coreteam_card_view,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val currentData = dataList[position]
        if(currentData.imageUri != null)
        {
            Picasso.get().load(currentData.imageUri).into(holder.image)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.org_image_c)
        val name = view.findViewById<ImageView>(R.id.org_name_c)
        val position = view.findViewById<ImageView>(R.id.org_position_c)
        val team = view.findViewById<ImageView>(R.id.org_team_c)
        val linkedIn = view.findViewById<ImageView>(R.id.org_linkedin_c)
        val insta = view.findViewById<ImageView>(R.id.org_insta_c)

    }
}