package ru.fefu.activitytracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.lists.ListItem
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ItemDataBinding
import ru.fefu.activitytracker.databinding.ModelItemBinding

class ItemAdapter(private val list: List<ListItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemClickListener: (Int) -> Unit = {}

    fun setItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is ListItem.Item) R.layout.model_item
               else R.layout.item_data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.model_item) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.model_item, parent, false)
            ViewHolder(view)
        }else  if (viewType == R.layout.item_data){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
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

    companion object {
        fun getNoun(number: Long, one: String, two: String, five: String) : String {
            var n = number
            n %= 100
            if (n in 5..20) {
                return five
            }
            n %= 10
            if (n == 1L) {
                return one
            }
            if (n in 2..4) {
                return two
            }
            return five
        }
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun addItem(model: Item) {
//        list.add(model)
//        notifyDataSetChanged()
//    }

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = ModelItemBinding.bind(item)

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

    inner class ViewHolderDate(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDataBinding.bind(itemView)

        fun bind(date: ListItem.Date) = with(binding) {
            textDateShablon.text = date.date
        }
    }

}


