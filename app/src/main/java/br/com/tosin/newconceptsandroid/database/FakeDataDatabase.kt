package br.com.tosin.newconceptsandroid.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
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