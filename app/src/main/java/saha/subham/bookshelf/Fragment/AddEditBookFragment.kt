package saha.subham.bookshelf.Fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import saha.subham.bookshelf.Model.Book
import saha.subham.bookshelf.Model.Villain
import saha.subham.bookshelf.MyApplication
import saha.subham.bookshelf.R
import saha.subham.bookshelf.ViewModel.BookViewModel
import saha.subham.bookshelf.ViewModel.BooksViewModelFactory
import saha.subham.bookshelf.databinding.FragmentAddEditBookBinding
import saha.subham.bookshelf.databinding.FragmentBookDetailBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddEditBookFragment : Fragment() {

    private var _bookAddEditFragment : FragmentAddEditBookBinding? = null
    private val bookAddEditFragment get() = _bookAddEditFragment!!
    private lateinit var bookViewModel: BookViewModel
    private var bookId: Int = 0
    private var pages: Int = 0
    private var year: Int = 2024
    private var ISBN:String = ""
    private var handle:String = ""
    private var villains:List<Villain> = listOf<Villain>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bookAddEditFragment = FragmentAddEditBookBinding.inflate(inflater, container, false)

        return bookAddEditFragment.root
    }

    private fun callData() {
        val repository = (requireActivity().application as MyApplication).bookRepository

        bookViewModel = ViewModelProvider(this, BooksViewModelFactory(repository))[BookViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val book = arguments?.getParcelable<Book>("book")

        book?.let {
            bookId = it.id
            ISBN = it.ISBN
            handle = it.handle
            pages = it.Pages
            villains = it.villains
            year = it.Year
            bookAddEditFragment.heading.text = resources.getString(R.string.editBooks)
            bookAddEditFragment.title.setText(it.Title)
            bookAddEditFragment.author.setText(it.Publisher)
            val details = it.Notes.joinToString(separator = "\n")
            bookAddEditFragment.description.setText(details)
            bookAddEditFragment.saveLabel.text = resources.getString(R.string.update)
        }
        bookAddEditFragment.save.setOnClickListener {
            saveData()
        }
        bookAddEditFragment.back.setOnClickListener{
            redirect()
        }
        bookAddEditFragment.cancel.setOnClickListener{
            redirect()
        }
    }

    private fun saveData() {
        val title = bookAddEditFragment.title.text.toString()
        val author = bookAddEditFragment.author.text.toString()
        val description = bookAddEditFragment.description.text.toString()

        if(validate(title, author, description)) {
            if(bookId == 0) {
                val saveBook = Book(ISBN, listOf(description),pages,author,title,year,getCurrentTime(),handle,bookId,villains)
                bookViewModel.addBooks(saveBook)
                Toast.makeText(requireContext(), "Book Save Successfully", Toast.LENGTH_SHORT).show()
            } else {
                val updateBook = Book(ISBN, listOf(description),pages,author,title,year,getCurrentTime(),handle,bookId,villains)
                bookViewModel.updateBooks(updateBook)
                Toast.makeText(requireContext(), "Book Updated Successfully", Toast.LENGTH_SHORT).show()
            }
            redirect()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validate(title: String, author: String, description: String) : Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(author) && TextUtils.isEmpty(description))
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun redirect() {
        findNavController().navigate(R.id.action_addEditBookFragment_to_bookListFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _bookAddEditFragment =  null
    }

}