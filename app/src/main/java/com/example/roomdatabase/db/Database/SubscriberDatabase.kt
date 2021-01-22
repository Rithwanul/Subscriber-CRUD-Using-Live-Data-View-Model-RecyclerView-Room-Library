package com.example.roomdatabase.db.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdatabase.db.Dao.SubscriberDAO
import com.example.roomdatabase.db.Entity.Subscriber

@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDatabase : RoomDatabase() {

    abstract val subscriberDAO : SubscriberDAO

    companion object{
        @Volatile
        private var INSTANCE : SubscriberDatabase? = null

        fun getInstance(context : Context) : SubscriberDatabase {
            synchronized(this) {
                var instance: SubscriberDatabase? = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SubscriberDatabase::class.java,
                        "subscriber_data_database"
                    ).build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}