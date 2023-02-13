package com.example.acronymproject.adapters

import android.content.Context
import com.example.acronymproject.models.Longform
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.widget.Toast
import com.example.acronymproject.R
import com.example.acronymproject.databinding.LongformItemBinding
import java.util.ArrayList

/**
 * This is LongformRecyclerViewAdapter class to display list of large forms in recyclerview.
 */

class LongformRecyclerViewAdapter(
    var longformArrayList: ArrayList<Longform>,
    var context: Context
) : RecyclerView.Adapter<LongformRecyclerViewAdapter.ViewHolder>(), ItemClickListener<Longform?> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<LongformItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.longform_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val longform = longformArrayList[position]
        holder.bind(longform, this)
    }

    override fun getItemCount(): Int {
        return longformArrayList.size
    }

    override fun itemClicked(item: Longform?) {
        //Show variations of longform
        //print("Under itemClicked" + item);
        Toast.makeText(context, item?.longForm, Toast.LENGTH_LONG ).show()
    }

    inner class ViewHolder(var itemRowBinding: LongformItemBinding) : RecyclerView.ViewHolder(
        itemRowBinding.root
    ) {
        fun bind(longform: Longform?, itemClickListener: ItemClickListener<*>?) {
            itemRowBinding.longform = longform
            itemRowBinding.itemClickListener = itemClickListener
            itemRowBinding.executePendingBindings()
        }
    }
}