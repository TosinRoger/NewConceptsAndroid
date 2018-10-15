package br.com.tosin.newconceptsandroid.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.tosin.newconceptsandroid.entity.FakeData

@Database(entities = [FakeData::class], version = 1)
abstract class FakeDataDatabase: RoomDatabase() {

    abstract fun fakeDataDao(): FakeDataDao

    companion object {
        @Volatile
        private var INSTANCE: FakeDataDatabase? = null

        fun getInstance(context: Context): FakeDataDatabase? {
            val temp = INSTANCE

            if (temp != null)
                return temp

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        FakeDataDatabase::class.java,
                        "fake_data.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}