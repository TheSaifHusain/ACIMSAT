package com.altamash.acimsat.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altamash.acimsat.R

class annuAdapter(var data: List<AnData>) : RecyclerView.Adapter<annuAdapter.myViewHolder>() {
    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Date = itemView.findViewById<TextView>(R.id.date)
        val Title = itemView.findViewById<TextView>(R.id.title)
        val Detail = itemView.findViewById<TextView>(R.id.detail)
        val Link = itemView.findViewById<TextView>(R.id.link)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_annu, parent, false)
        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.Date.text = data[position].Date
        holder.Title.text = data[position].Title
        holder.Detail.text = data[position].Detail
        holder.Link.text = data[position].Link

    }

    override fun getItemCount(): Int {
        return data.size
    }


}