package com.akmisoftware.bestpurchases.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.akmisoftware.bestpurchases.db.DateTypeConverter
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "events")
data class Event(
    @PrimaryKey
    @ColumnInfo(name = "eventId")
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "attendeesAmount")
    val attendeesAmount: Int,
    @TypeConverters(DateTypeConverter::class)
    @ColumnInfo(name = "date")
    val date: Date,
    @TypeConverters(DateTypeConverter::class)
    @ColumnInfo(name = "time")
    val time: Date,
    @ColumnInfo(name = "image")
    val image: Int,
    @ColumnInfo(name = "locationName")
    val locationName: String,
    @ColumnInfo(name = "latlng")
    val latlng: String,
    @ColumnInfo(name = "description")
    val description: String
) : Parcelable