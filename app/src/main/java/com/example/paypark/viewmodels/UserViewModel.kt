package com.example.paypark.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.paypark.database.PayParkDatabase
import com.example.paypark.database.UserRepository
import com.example.paypark.model.User

//intermediate between view and data you want to manipulate for the views
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepo: UserRepository

    var allUsers: LiveData<List<User>>

    init {
        val userDao = PayParkDatabase.getDatabase(application).userDao()
        userRepo = UserRepository(userDao)

        allUsers = userRepo.allUsers
    }

    /**
     * insertUser() method will create a new user record in the database
     */
    fun insertAll(){}
}