package com.timeforpublic.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.timeforpublic.data.local.dao.OfficeDao
import com.timeforpublic.data.local.dao.SavedItemDao
import com.timeforpublic.data.local.dao.SchemeDao
import com.timeforpublic.data.local.dao.ServiceDao
import com.timeforpublic.data.local.entity.OfficeEntity
import com.timeforpublic.data.local.entity.SavedItemEntity
import com.timeforpublic.data.local.entity.SchemeEntity
import com.timeforpublic.data.local.entity.ServiceEntity

@Database(
    entities = [
        ServiceEntity::class,
        SchemeEntity::class,
        OfficeEntity::class,
        SavedItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun serviceDao(): ServiceDao
    abstract fun schemeDao(): SchemeDao
    abstract fun officeDao(): OfficeDao
    abstract fun savedItemDao(): SavedItemDao
}
