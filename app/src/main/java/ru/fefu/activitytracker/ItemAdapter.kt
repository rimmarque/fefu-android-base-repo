package ru.fefu.activitytracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ItemDateBinding
import ru.fefu.activitytracker.databinding.ItemListBinding
import ru.fefu.activitytracker.lists.ListItem

class ItemAdapter(items: List<ListItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = items.toMutableList()

    private var itemClickListener: (Int) -> Unit = {}

    fun setItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is ListItem.Item) R.layout.item_list
        else R.layout.item_date
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_list) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
            ViewHolder(view)
        }else  if (viewType == R.layout.item_date){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
            ViewHolderDate(view)
        } else throw IllegalArgumentException("Invalid viewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ViewHolder -> holder.bind(list[position] as ListItem.Item)
            is ViewHolderDate -> holder.bind(list[position] as ListItem.Date)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = ItemListBinding.bind(item)

        init {
            item.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    itemClickListener.invoke(position)
            }
        }

        fun bind(model: ListItem.Item) = with(binding) {
            textDistance.text = model.distance
            textTime.text = model.time
            textActivity.text = model.activity
            textDate.text = model.date
            textUsers.text = model.user
        }
    }

    inner class ViewHolderDate(item: View): RecyclerView.ViewHolder(item) {
        private val binding = ItemDateBinding.bind(item)

        fun bind(date: ListItem.Date) = with(binding) {
            textDateShablon.text = date.date
        }
    }
}