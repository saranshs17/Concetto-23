package com.iitism.concetto.ui.merchandisefragment

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iitism.concetto.R
import com.squareup.picasso.Picasso

class CorouselAdapter(private val corouselDataList: ArrayList<Int>) : RecyclerView.Adapter<CorouselAdapter.CarouselItemViewHolder>()
{
    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_corousel,parent,false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return corouselDataList.size
    }


    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.image_merchandise)
        Glide.with(holder.itemView.context)
            .load(corouselDataList[position])
            .placeholder(R.drawable.merchandise_2)
            .error(R.drawable.merchandise_2)
            .into(imageView)
    }

}