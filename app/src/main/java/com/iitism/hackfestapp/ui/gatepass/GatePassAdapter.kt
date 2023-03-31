package com.iitism.hackfestapp.ui.gatepass

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iitism.hackfestapp.databinding.QrViewCardBinding


class GatePassAdapter(
    private val context: Context?,
    private val qrlist: List<GatePassModel>
    ) : RecyclerView.Adapter<GatePassAdapter.ViewHolder>() {


    private lateinit var binding: QrViewCardBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        binding = QrViewCardBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.qrView.setImageBitmap(qrlist[position].QrImage)
        holder.binding.qrViewText.text = qrlist[position].textValue
    }

    override fun getItemCount(): Int {
        return qrlist.size
    }

    class ViewHolder(val binding: QrViewCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}