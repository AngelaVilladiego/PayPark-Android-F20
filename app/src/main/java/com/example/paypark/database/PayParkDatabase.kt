package com.example.paypark.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.paypark.R
import com.example.paypark.model.User

@Database(entities = arrayOf(User::class/*, other classes*/), version = 1)
abstract class PayParkDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    //companion so we dont need to create an instance of this class in order to access below
    companion object{

        //volatile indicates whatever memory associated with instance stays there for as long the
        // app is loaded and further
        //uses permanent storage memory
        @Volatile
        private var INSTANCE: PayParkDatabase? = null

        //in the context of the application calling this we want to get the database
        fun getDatabase(context: Context) : PayParkDatabase{
            val tempInstance = INSTANCE

            //if database instance already exists, return existing instance
            if (tempInstance != null){
                return tempInstance
            }
            //else create new database synchronized because app wont function if there's no database
            //so we're asking the app to wait for this to be finished before continuing
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PayParkDatabase::class.java,
                    R.string.database_name.toString()
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}