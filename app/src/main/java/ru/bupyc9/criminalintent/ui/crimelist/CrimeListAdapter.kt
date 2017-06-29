package ru.bupyc9.criminalintent.ui.crimelist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.bupyc9.criminalintent.R
import ru.bupyc9.criminalintent.models.Crime
import kotlinx.android.synthetic.main.item_crime.view.*

class CrimeListAdapter(private val mItems: List<Crime>) : RecyclerView.Adapter<CrimeListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val viewHolder = holder as ViewHolder
        val crime = getItem(position)

        viewHolder.bind(crime)
    }

    private fun getItem(position: Int): Crime {
        return mItems.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_crime, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(crime: Crime) {
            itemView.crime_title.text = crime.title
            itemView.crime_date.text = crime.date.toString()
            itemView.crime_solved.isChecked = crime.solved
        }
    }
}