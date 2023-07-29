package com.mufiid.composestockmarketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mufiid.composestockmarketapp.BuildConfig
import com.mufiid.composestockmarketapp.data.local.dao.StockDao

@Database(
    entities = [CompanyListingEntity::class],
    version = BuildConfig.LOCAL_DATABASE_VERSION,
    exportSchema = false,
)
abstract class StockDatabase: RoomDatabase() {
    abstract val dao: StockDao
}