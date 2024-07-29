package saha.subham.bookshelf.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import saha.subham.bookshelf.Model.Book
import saha.subham.bookshelf.Model.Data
import saha.subham.bookshelf.Repository.BookRepository

class BookViewModel(private val bookRepository: BookRepository) :ViewModel() {

    val bookLiveData = MutableLiveData<Book?>()

    init {
        viewModelScope.launch (Dispatchers.IO){
            bookRepository.getBooks()
        }
    }

    val books : LiveData<Data>
    get() = bookRepository.books

    fun updateBooks(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            bookRepository.updateBook(book)
        }
    }

    fun addBooks(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            bookRepository.addBook(book)
        }
    }

    fun removeBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            bookRepository.removeBook(book)
        }
    }

    fun getBook(id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val book = bookRepository.getBook(id)
            bookLiveData.postValue(book)
           // bookRepository.getBook(id)
        }
    }
}