package br.com.tosin.newconceptsandroid.repository.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import br.com.tosin.newconceptsandroid.entity.FakeData

@Dao
interface FakeDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fakeData: FakeData)

    @Query("DELETE FROM fake_data_table")
    fun deleteAll()

    @Query("SELECT * from fake_data_table ORDER BY `index` ASC")
    fun getAll(): List<FakeData>

    @Query("SELECT * from fake_data_table")
    fun getAllAlive(): LiveData<List<FakeData>>

    @Delete()
    fun removeById(fakeData: FakeData)

    @Query("SELECT * FROM fake_data_table WHERE Id IN(:fakeDataId)")
    fun fetchById(fakeDataId: String): FakeData?
}