package com.timeforpublic.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_items")
data class SavedItemEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val department: String,
    val description: String,
    val eligibilitySummary: String = "",
    val officialPortalUrl: String = "",
    val estimatedProcessingDays: Int = 0,
    val savedAt: Long = System.currentTimeMillis()
)
