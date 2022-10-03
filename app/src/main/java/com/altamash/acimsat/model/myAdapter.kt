package com.altamash.acimsat.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altamash.acimsat.R

class myAdapter(var data: List<ResultDetail>) : RecyclerView.Adapter<myAdapter.myViewHolder>() {
    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Name = itemView.findViewById<TextView>(R.id.Name)
        val City = itemView.findViewById<TextView>(R.id.City)
        val Phone = itemView.findViewById<TextView>(R.id.Phone)
        val Email = itemView.findViewById<TextView>(R.id.Email)
        val ObResult = itemView.findViewById<TextView>(R.id.ObResult)
        val Q1 = itemView.findViewById<TextView>(R.id.Question1)
        val Q2 = itemView.findViewById<TextView>(R.id.Question2)
        val Q3 = itemView.findViewById<TextView>(R.id.Question3)
        val Q4 = itemView.findViewById<TextView>(R.id.Question4)
        val Q5 = itemView.findViewById<TextView>(R.id.Question5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view, parent, false)
        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.Name.text = data[position].Name
        holder.Phone.text = data[position].Phone
        holder.City.text = data[position].City
        holder.Email.text = data[position].Email
        holder.ObResult.text = data[position].ObResult
        holder.Q1.text = data[position].Question1
        holder.Q2.text = data[position].Question2
        holder.Q3.text = data[position].Question3
        holder.Q4.text = data[position].Question4
        holder.Q5.text = data[position].Question5
    }

    override fun getItemCount(): Int {
        return data.size
    }


}