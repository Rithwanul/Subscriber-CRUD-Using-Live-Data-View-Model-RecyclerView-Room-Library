package com.example.roomdatabase.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.db.Database.SubscriberDatabase
import com.example.roomdatabase.R
import com.example.roomdatabase.adapter.SubscriberRecyclerViewAdapter
import com.example.roomdatabase.repository.SubscriberRepository
import com.example.roomdatabase.viewmodel.SubscriberViewModel
import com.example.roomdatabase.viewmodelfactory.SubscriberViewModelFactory
import com.example.roomdatabase.databinding.ActivityMainBinding
import com.example.roomdatabase.db.Entity.Subscriber

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var subscriberViewModel : SubscriberViewModel
    private lateinit var adapter: SubscriberRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository, applicationContext)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.subscriberViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView(){
        binding.recyclerViewSubscriber.layoutManager = LinearLayoutManager(this)
        adapter = SubscriberRecyclerViewAdapter({selectedItem : Subscriber -> subscriberItemClicked(selectedItem)})
        binding.recyclerViewSubscriber.adapter = adapter
        displaySubscriberList()
    }

    private fun displaySubscriberList(){
        subscriberViewModel.subscribers.observe(this, Observer {
            adapter.setSubscriberList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun subscriberItemClicked(subscriber : Subscriber){
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}