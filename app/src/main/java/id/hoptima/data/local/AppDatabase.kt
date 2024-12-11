package id.hoptima.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.hoptima.data.local.dao.PropertyDao
import id.hoptima.data.local.entity.PropertyEntity

@Database(entities = [PropertyEntity::class], version = 1, exportSchema = true)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun propertyDao(): PropertyDao
}