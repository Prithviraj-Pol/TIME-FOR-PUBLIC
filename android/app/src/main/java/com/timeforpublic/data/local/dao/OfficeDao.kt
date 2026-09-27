package com.timeforpublic.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timeforpublic.data.local.entity.OfficeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OfficeDao {

    @Query("SELECT * FROM offices")
    fun getAllOffices(): Flow<List<OfficeEntity>>

    @Query("SELECT * FROM offices WHERE id = :id")
    fun getOfficeById(id: String): Flow<OfficeEntity?>

    @Query("SELECT * FROM offices WHERE department = :department")
    fun getOfficesByDepartment(department: String): Flow<List<OfficeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertOffices(offices: List<OfficeEntity>)
}
