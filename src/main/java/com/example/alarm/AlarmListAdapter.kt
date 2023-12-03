package com.example.alarm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlarmListAdapter(private val context: Context, private val alarms: List<Alarm>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // View types
    private val VIEW_TYPE_ALARM = 0

    // Click listener interface
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    // Define your view holder for the alarm item
    inner class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.alarm_time_text)
        val labelTextView: TextView = itemView.findViewById(R.id.alarm_label_text)
    }

    // Override necessary methods for RecyclerView.Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Inflate your alarm item layout and return the corresponding view holder
        val inflater = LayoutInflater.from(context)
        return when (viewType) {
            VIEW_TYPE_ALARM -> {
                val itemView = inflater.inflate(R.layout.alarm_item, parent, false)
                AlarmViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Bind data to your view holder based on position
        when (holder) {
            is AlarmViewHolder -> {
                val alarm = alarms[position]
                // Bind alarm data to the view holder
                holder.timeTextView.text = alarm.time.toString()
                holder.labelTextView.text = alarm.label

                // Set click listener on the entire item view
                holder.itemView.setOnClickListener {
                    onItemClickListener?.onItemClick(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    override fun getItemViewType(position: Int): Int {
        // Return the view type based on the position
        return VIEW_TYPE_ALARM
    }

    // Add any additional methods or interfaces you need for interaction
}
