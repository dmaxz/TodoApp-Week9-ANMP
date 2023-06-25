package com.example.todoapp.util
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todoapp.model.TodoDatabase

val DB_NAME = "newtododb"

fun buildDB(context: Context):TodoDatabase{
    val db = Room.databaseBuilder(
        context.applicationContext,
        TodoDatabase::class.java,
        "newtododb"
    ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()

    return db
}

val MIGRATION_1_2 = object :Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("alter table todo add column priority integer default 3 not null")
    }
}

val MIGRATION_2_3 = object :Migration(2,3){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("alter table todo add column is_done integer default 0 not null")
    }
    // dikarenakan tidak ada tipe data native yang hanya dapat merepresentasikan boolean ( 0 dan 1 )
    // sebagai penggantinya, SQLite menggunakan integer yang dimana 0 bernilai false
    // dan semua angka selain 0 akan bernilai true (negatif maupun positif)
}