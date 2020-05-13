package com.example.simplecalculator

import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplecalculator.database.DatabaseModel
import kotlinx.android.synthetic.main.list.view.*

class theAdapter() : RecyclerView.Adapter<theAdapter.RecyclerViewHolder>(){
    private var recyclerList: List<DatabaseModel> = emptyList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): theAdapter.RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list,parent, false)
        return RecyclerViewHolder(itemView)
    }

    override fun getItemCount() = recyclerList.size

    override fun onBindViewHolder(holder: theAdapter.RecyclerViewHolder, position: Int) {
        holder.NumberOne.text = recyclerList[position].one.toString()
        holder.NumberTwo.text = recyclerList[position].two.toString()
        holder.Expected.text = recyclerList[position].expected
        holder.Passed.text = recyclerList[position].passed
        holder.Response.text = recyclerList[position].result
        if (recyclerList[position].expected == recyclerList[position].result ){
        holder.backgroundView.setBackgroundResource(R.color.navajo_white)
        }else{
            holder.backgroundView.setBackgroundResource(R.color.light_pink)
        }

    }
    fun setResults(results: List<DatabaseModel>){
        this.recyclerList = results
        notifyDataSetChanged()
    }

    class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var NumberOne: TextView = itemView.number_1_value
        var NumberTwo: TextView = itemView.number_2_value
        var Response: TextView = itemView.response_value
        var Expected: TextView = itemView.expected_value
        var Passed: TextView = itemView.passed_value
        var backgroundView: CardView = itemView.card_view


    }
}