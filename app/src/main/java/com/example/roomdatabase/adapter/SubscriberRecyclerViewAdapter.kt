package com.example.roomdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.LayoutSubscriberItemBinding
import com.example.roomdatabase.db.Entity.Subscriber

class SubscriberRecyclerViewAdapter (
            private val subscriberClickListener: (Subscriber) -> Unit
        )
    :  RecyclerView.Adapter<SubscriberViewHolder>()
{

    private val subscribers = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val binding : LayoutSubscriberItemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.layout_subscriber_item, parent, false)
        return SubscriberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.bind(subscribers[position], subscriberClickListener)
    }

    override fun getItemCount(): Int = subscribers.size

    fun setSubscriberList(subscribers : List<Subscriber>){
        this.subscribers.clear()
        this.subscribers.addAll(subscribers)
    }

}

class SubscriberViewHolder(
        private val binding : LayoutSubscriberItemBinding
)
    : RecyclerView.ViewHolder(binding.root){

        fun bind(
                subscriber: Subscriber,
                subscriberClickListener: (Subscriber) -> Unit
        ){
            binding.textSubscriberName.text         =       subscriber.name
            binding.textSubscriberEmail.text        =       subscriber.email
            binding.cardSubscriberItem.setOnClickListener {
                subscriberClickListener(subscriber)
            }
        }
}