package com.example.roomdatabase.repository

import com.example.roomdatabase.db.Dao.SubscriberDAO
import com.example.roomdatabase.db.Entity.Subscriber

class SubscriberRepository(private val dao : SubscriberDAO) {

    val subscribers = dao.getAllSubscribers()

    suspend fun insert(subscriber : Subscriber) : Long{
        return dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber : Subscriber) : Int{
        return dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber : Subscriber) : Int{
        return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll() : Int{
        return dao.deleteAllSubscribers()
    }

}