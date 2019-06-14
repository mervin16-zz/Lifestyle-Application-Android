package com.th3pl4gu3.lifestyle.ui.home.section_tobuy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.databinding.CustomRecyclerviewListTobuyBinding
import com.th3pl4gu3.lifestyle.databinding.CustomRecyclerviewListTodoBinding

class ToBuyAdapter(private val clickListener: ToBuyListener) : ListAdapter<ToBuy, ToBuyAdapter.ViewHolder>(ToDoDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CustomRecyclerviewListTobuyBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(toBuy: ToBuy, clickListener: ToBuyListener) {
            binding.myToBuy = toBuy
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomRecyclerviewListTobuyBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ToDoDiffCallback: DiffUtil.ItemCallback<ToBuy>() {

    override fun areItemsTheSame(oldItem: ToBuy, newItem: ToBuy): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: ToBuy, newItem: ToBuy): Boolean {
        return oldItem == newItem
    }

}

class ToBuyListener(val clickListener: (toBuy: ToBuy) -> Unit){
    fun onClick(toBuy: ToBuy) = clickListener(toBuy)
}
