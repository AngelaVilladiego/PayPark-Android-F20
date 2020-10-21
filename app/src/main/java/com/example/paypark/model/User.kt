package com.example.paypark.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.example.paypark.utils.DateConverter
import java.util.*

@Entity(tableName = "Users", primaryKeys = arrayOf("email"))
@TypeConverters(*arrayOf(DateConverter::class)) /* * star means that the array could be empty */
data class User (
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "phone_number") var phoneNumber: String?,
    @ColumnInfo(name = "car_plate") var carPlate: String,
    @ColumnInfo(name = "gender") var gender: String?,
    @ColumnInfo(name = "credit_card_number") var creditCardNumber: String?,
    @ColumnInfo(name = "name_on_card") var nameOnCard: String?,
    @ColumnInfo(name = "cvv") var cvv: Int?,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "expiry_date") var expiryDate: Date?
){
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        0,
        "",
        Date()
    )
}

//Room database can store TEXT (for strings), INT (for ints and long), REAL (doubles and floats)
//BLOB (multimedia), UNDEFINED (not specifying type, null/empty)
