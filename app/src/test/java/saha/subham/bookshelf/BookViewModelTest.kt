package saha.subham.bookshelf

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import saha.subham.bookshelf.Model.Book
import saha.subham.bookshelf.Model.Data
import saha.subham.bookshelf.Model.Villain
import saha.subham.bookshelf.Repository.BookRepository
import saha.subham.bookshelf.ViewModel.BookViewModel

@RunWith(MockitoJUnitRunner::class)
class BookViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var bookRepository: BookRepository

    private lateinit var bookViewModel: BookViewModel

    @Before
    @ExperimentalCoroutinesApi
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        bookViewModel = BookViewModel(bookRepository)
    }

    @After
    @ExperimentalCoroutinesApi
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testGetBooks() = runBlockingTest {
        val data = Data(emptyList()) // Assuming Data class has a list of books
        `when`(bookRepository.books).thenReturn(MutableLiveData(data))

        val observer = mock(Observer::class.java) as Observer<Data>
        bookViewModel.books.observeForever(observer)

        bookViewModel.books.value?.let {
            Assert.assertEquals(data, it)
        }
    }

    @Test
    fun testAddBooks() = runBlockingTest {
        var villains:List<Villain> = listOf<Villain>()
        val book = Book("1", listOf("description"), 0, "Subham Saha", "Test", 2024, "", "", 0, villains) // Assuming Book has id, title, and author
        bookViewModel.addBooks(book)
        verify(bookRepository).addBook(book)
    }

    @Test
    fun testUpdateBooks() = runBlockingTest {
        var villains:List<Villain> = listOf<Villain>()
        val book = Book("1", listOf("description"), 0, "Subham Saha", "Test", 2024, "", "", 64, villains)
        bookViewModel.updateBooks(book)
        verify(bookRepository).updateBook(book)
    }

    @Test
    fun testRemoveBook() = runBlockingTest {
        var villains:List<Villain> = listOf<Villain>()
        val book = Book("1", listOf("description"), 0, "Subham Saha", "Test", 2024, "", "", 64, villains)
        bookViewModel.removeBook(book)
        verify(bookRepository).removeBook(book)
    }

    @Test
    fun testGetBook() = runBlockingTest {
        val book = Book("1", listOf("description"), 0, "Subham Saha", "Test", 2024, "", "", 64, emptyList())
        `when`(bookRepository.getBook(1)).thenReturn(book)

        val observer = mock(Observer::class.java) as Observer<Book?>
        bookViewModel.bookLiveData.observeForever(observer)

        bookViewModel.getBook(1)

        // Capture the argument passed to onChanged
        val argumentCaptor = ArgumentCaptor.forClass(Book::class.java)
        verify(observer).onChanged(argumentCaptor.capture())

        // Assert the captured value
        Assert.assertEquals(book, argumentCaptor.value)
    }
}