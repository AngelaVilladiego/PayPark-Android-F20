package com.example.paypark.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.paypark.database.PayParkDatabase
import com.example.paypark.database.UserRepository
import com.example.paypark.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//intermediate between view and data you want to manipulate for the views
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepo: UserRepository

    var allUsers: LiveData<List<User>> //livedata one way flow

    private var matchedUser : MutableLiveData<User>? //can be changed while observing

    init {
        val userDao = PayParkDatabase.getDatabase(application).userDao()
        userRepo = UserRepository(userDao)

        allUsers = userRepo.allUsers

        matchedUser = MutableLiveData()
    }

    /**
     * insertAll() method will create a new user record in the database
     */
    fun insertAll(user: User) = viewModelScope.launch(Dispatchers.IO){
        userRepo.insertAll(user)
    }

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepo.updateUser(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepo.deleteUser(user)
    }

    fun deleteUserByEmail(email: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepo.deleteUserByEmail(email)
    }

    private fun getUserByLoginInfoCoroutine(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        val user : User? = userRepo.getUserByLoginInfo(email, password)
        matchedUser?.postValue(user) //notifies all observers that it has been updated
    }

    fun getUserByLoginInfo(email: String, password: String) : MutableLiveData<User>? {
        getUserByLoginInfoCoroutine(email, password)

        return matchedUser
    }

    private fun getUserByEmailCoroutine(email: String) = viewModelScope.launch(Dispatchers.IO) {
        val user: User? = userRepo.getUserByEmail(email)
        matchedUser?.postValue(user)
    }

    fun getUserByEamil(email: String) : MutableLiveData<User>? {
        getUserByEmailCoroutine(email)

        Log.d("UserViewModel : ", matchedUser.toString())

        return matchedUser
    }
}