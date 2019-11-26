package com.akmisoftware.bestpurchases.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Event(val name: String, val attendeesAmount: Int, val date: Date, val time: String, val image: Int) : Parcelable