package ru.samarin.prodev.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_item_recyclerview.view.*
import ru.samarin.prodev.R
import ru.samarin.prodev.model.data.DataModel

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerViewHolder>() {

    private var data: List<DataModel> = arrayListOf()

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.history_item_recyclerview, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.header_history_item_recyclerview.text = data.text
                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "on click ${data.text}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

