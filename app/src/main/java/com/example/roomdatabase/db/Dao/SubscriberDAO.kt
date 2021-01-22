package com.example.roomdatabase.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomdatabase.db.Entity.Subscriber

@Dao
interface SubscriberDAO {

    @Insert
    suspend fun insertSubscriber(subscriber : Subscriber) : Long

    @Update
    suspend fun updateSubscriber(subscriber : Subscriber) : Int

    @Delete
    suspend fun deleteSubscriber(subscriber : Subscriber) : Int

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAllSubscribers() : Int

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers() : LiveData<List<Subscriber>>

}