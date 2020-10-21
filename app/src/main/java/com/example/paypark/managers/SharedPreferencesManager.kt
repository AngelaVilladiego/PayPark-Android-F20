package com.example.paypark.managers

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object SharedPreferencesManager {
    private var sharedPreferences: SharedPreferences? = null

    val EMAIL = "KEY_EMAIL"
    val PASSWORD = "KEY_PASSWORD"

    fun init (context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.packageName,
                Activity.MODE_PRIVATE)
        }
    }

    fun write(key: String?, value: String?) {
//        sharedPreferences?.edit()!!.putString(key, value).commit()
        //synchronous

//        with(sharedPreferences!!.edit()) {
//            putString(key,value)
//            commit()
//        }
        //synchronous

        apply { sharedPreferences!!.edit().putString(key, value).apply()}
        //asynchronous, better so you don't halt your UI
    }

    fun read(key: String?, defaultValue: String?): String? {
        with (sharedPreferences) {
//            return sharedPreferences!!.getString(key, defaultValue)
            if (this!!.contains(key)) {
                Log.d("SharedPrefManage +++++ : ", "key found")
                return sharedPreferences!!.getString(key, defaultValue)
            }
        }
        Log.d("SharedPrefManage +++++ : ", "key not found")
        return defaultValue
    }

    fun remove(key: String) {
        if (sharedPreferences != null && sharedPreferences!!.contains(key))
        apply { sharedPreferences?.edit()!!.remove(key).apply()}
    }

    fun removeAll(){
        with (sharedPreferences!!.edit()) {
            if (sharedPreferences != null && sharedPreferences!!.contains(EMAIL)
                && sharedPreferences!!.contains(PASSWORD))
            remove(EMAIL)
            remove(PASSWORD)
            apply() //asynchronously
//            commit() //syncronously
        }
    }

}