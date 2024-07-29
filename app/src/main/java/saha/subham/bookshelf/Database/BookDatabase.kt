package saha.subham.bookshelf.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import saha.subham.bookshelf.Model.Book

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase(){

    abstract fun bookDao() : BookDao

    companion object{
        private var INSTANCE: BookDatabase ?= null

        fun getDatabase(context: Context): BookDatabase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    BookDatabase::class.java,
                    "booksDB"
                ).build()
            }

            return INSTANCE!!
        }

    }
}