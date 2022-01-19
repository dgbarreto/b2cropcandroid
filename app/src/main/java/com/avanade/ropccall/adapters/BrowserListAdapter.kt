package com.avanade.ropccall.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.avanade.ropccall.R

class BrowserListAdapter(private val dataSet : Array<Pair<String,String>>) : RecyclerView.Adapter<BrowserListAdapter.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        lateinit var tvNamespace : TextView
        lateinit var tvSignature : TextView

        init {
            tvNamespace = view.findViewById(R.id.tvNamespace)
            tvSignature = view.findViewById(R.id.tvHashSignature)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.browser_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.tvNamespace.setText(item.first)
        holder.tvSignature.setText(item.second)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}