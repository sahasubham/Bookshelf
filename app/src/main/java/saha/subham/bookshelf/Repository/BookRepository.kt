package saha.subham.bookshelf.Repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import saha.subham.bookshelf.Api.ApiInterface
import saha.subham.bookshelf.Database.BookDatabase
import saha.subham.bookshelf.Model.Book
import saha.subham.bookshelf.Model.Data
import saha.subham.bookshelf.Utils.NetworkAvailability

class BookRepository(private val apiInterface: ApiInterface, private val bookDatabase: BookDatabase, private val applicationContext: Context) {

    private val bookLiveData = MutableLiveData<Data>()

    val books: LiveData<Data>
    get() = bookLiveData


    suspend fun getBooks() {
        //val localBookIds = getLocalBookIds()
        val localBooks = bookDatabase.bookDao().getBooks()
        if(localBooks.isNotEmpty()) {
            val bookList = Data(localBooks)
            bookLiveData.postValue(bookList)
        } else {
            if(NetworkAvailability.isInternetAvailable(applicationContext)!!) {
                val result = apiInterface.getBooks()
                if(result.body() != null) {
                    bookDatabase.bookDao().insertBooks(result.body()!!.data)
                    bookLiveData.postValue(result.body())
                }
            } else {
                val bookList = Data(localBooks)
                bookLiveData.postValue(bookList)
            }
        }
//        if(NetworkAvailability.isInternetAvailable(applicationContext)!!) {
//            Log.e("Internet Available","YES")
//            val apiBookIds = getApiBookIds()
//            if (localBookIds == apiBookIds) {
//                //Toast.makeText(applicationContext, "LOCAL BOOKS", Toast.LENGTH_SHORT).show()
//                Log.e("Internet Available","LOCAL DB")
//                val localBooks = bookDatabase.bookDao().getBooks()
//                val bookList = Data(localBooks)
//                bookLiveData.postValue(bookList)
//            } else {
//                Log.e("Internet Available","API CALL")
//                val result = apiInterface.getBooks()
//                if(result.body() != null) {
//                    bookDatabase.bookDao().insertBooks(result.body()!!.data)
//                    bookLiveData.postValue(result.body())
//                }
//            }
//        } else {
//            Log.e("Internet Available","No Internet LOCAL DB")
//            val book = bookDatabase.bookDao().getBooks()
//
//            val bookList = Data(book)
//
//            bookLiveData.postValue(bookList)
//        }

    }
    suspend fun getLocalBookIds(): Set<Int> {
        return bookDatabase.bookDao().getBooks().map { it.id }.toSet()
    }

    suspend fun getApiBookIds(): Set<Int> {
        val result = apiInterface.getBooks()
        return result.body()?.data?.map { it.id }?.toSet() ?: emptySet()
    }


    suspend fun updateBook(book: Book) {
        bookDatabase.bookDao().updateBooks(book)
    }

    suspend fun addBook(book: Book) {
        bookDatabase.bookDao().addBooks(book)
    }

    suspend fun removeBook(book:Book) {
        bookDatabase.bookDao().removeBook(book)
    }

    suspend fun getBook(id: Int): Book? {
        return bookDatabase.bookDao().getBookById(id)
//        val bookList = localBooks
//        bookLiveData.postValue(localBooks)

    }
}