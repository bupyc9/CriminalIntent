package ru.bupyc9.criminalintent.ui.crimelist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.bupyc9.criminalintent.R
import ru.bupyc9.criminalintent.models.Crime
import kotlinx.android.synthetic.main.item_crime.view.*

class CrimeListAdapter(private val mItems: MutableList<Crime>) : RecyclerView.Adapter<CrimeListAdapter.ViewHolder>() {
    private var mOnClickListener: ((View?, Crime) -> Unit)? = null
    private var mOnLongClickListener: ((View?, Crime) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val viewHolder = holder as ViewHolder
        val crime = getItem(position)

        viewHolder.bind(crime)
    }

    private fun getItem(position: Int): Crime {
        return mItems[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_crime, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = mItems.size

    fun setOnClickListener(listener: (View?, Crime) -> Unit) {
        mOnClickListener = listener
    }

    fun setOnLongClick(listener: (View?, Crime) -> Unit) {
        mOnLongClickListener = listener
    }

    fun remove(crime: Crime): Boolean = mItems.remove(crime)

    fun getPosition(crime: Crime) = mItems.indexOf(crime)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener,
            View.OnLongClickListener {
        private lateinit var mCrime: Crime

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun bind(crime: Crime) {
            mCrime = crime
            itemView.crime_title.text = crime.title
            itemView.crime_date.text = crime.date.toString()
            itemView.crime_solved.isChecked = crime.solved
        }

        override fun onClick(view: View?) {
            mOnClickListener?.invoke(view, mCrime)
        }

        override fun onLongClick(view: View?): Boolean {
            mOnLongClickListener?.invoke(view, mCrime)
            return mOnLongClickListener != null
        }
    }
}