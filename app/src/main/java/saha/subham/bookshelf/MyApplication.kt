package saha.subham.bookshelf

import android.app.Application
import saha.subham.bookshelf.Api.ApiInterface
import saha.subham.bookshelf.Api.ApiUtilities
import saha.subham.bookshelf.Database.BookDatabase
import saha.subham.bookshelf.Repository.BookRepository

class MyApplication : Application() {
    lateinit var bookRepository: BookRepository
    override fun onCreate() {
        super.onCreate()

        val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)

        val bookDatabase = BookDatabase.getDatabase(applicationContext)

        bookRepository = BookRepository(apiInterface, bookDatabase, applicationContext)
    }
}