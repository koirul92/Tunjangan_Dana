package com.example.tunjangandana.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Dana(
    @PrimaryKey(autoGenerate = true) var id:Int?,
    @ColumnInfo(name = "keterangan") var keterangan: String,
    @ColumnInfo(name = "danaGoals") var danaGoals: String,
    @ColumnInfo(name = "danaMonth") var danaMonth: String,
): Parcelable
