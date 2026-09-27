package com.timeforpublic.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timeforpublic.data.local.entity.SchemeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SchemeDao {

    @Query("SELECT * FROM schemes")
    fun getAllSchemes(): Flow<List<SchemeEntity>>

    @Query("SELECT * FROM schemes WHERE id = :id")
    fun getSchemeById(id: String): Flow<SchemeEntity?>

    @Query("SELECT * FROM schemes WHERE category = :category")
    fun getSchemesByCategory(category: String): Flow<List<SchemeEntity>>

    @Query("SELECT * FROM schemes WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' OR department LIKE '%' || :query || '%'")
    fun searchSchemes(query: String): Flow<List<SchemeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSchemes(schemes: List<SchemeEntity>)
}
