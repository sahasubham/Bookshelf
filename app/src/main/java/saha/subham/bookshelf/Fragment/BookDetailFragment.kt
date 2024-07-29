package saha.subham.bookshelf.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import saha.subham.bookshelf.Model.Book
import saha.subham.bookshelf.MyApplication
import saha.subham.bookshelf.R
import saha.subham.bookshelf.ViewModel.BookViewModel
import saha.subham.bookshelf.ViewModel.BooksViewModelFactory
import saha.subham.bookshelf.databinding.FragmentBookDetailBinding
import saha.subham.bookshelf.databinding.FragmentBookListBinding

class BookDetailFragment : Fragment() {

    private var _bookDetailsFragment : FragmentBookDetailBinding? = null
    private val bookDetailFragment get() = _bookDetailsFragment!!
    private lateinit var bookId: String
    private lateinit var bookViewModel: BookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callData()
    }

    private fun callData() {
        val repository = (requireActivity().application as MyApplication).bookRepository

        bookViewModel = ViewModelProvider(
            this,
            BooksViewModelFactory(repository)
        )[BookViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bookDetailsFragment = FragmentBookDetailBinding.inflate(inflater, container, false)

        arguments?.let {
            bookId = it.getString("bookId") ?: ""
        }

        return bookDetailFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the Book object from the arguments
        if(bookId == "") {
            val book = arguments?.getParcelable<Book>("book")
            book?.let {
                bookDetailFragment.bookName.text = it.Title
                bookDetailFragment.publisherName.text = it.Publisher
                bookDetailFragment.year.text = it.Year.toString()
                val details = it.Notes.joinToString(separator = "\n")
                bookDetailFragment.bookDetails.text = details
            }
        } else {
            bookViewModel.getBook(bookId.toInt())
            bookViewModel.bookLiveData.observe(viewLifecycleOwner, Observer { book ->
                // Update your UI with the fetched book
                book?.let {
                    bookDetailFragment.bookName.text = it.Title
                    bookDetailFragment.publisherName.text = it.Publisher
                    bookDetailFragment.year.text = it.Year.toString()
                    val details = it.Notes.joinToString(separator = "\n")
                    bookDetailFragment.bookDetails.text = details
                }
            })

        }
        bookDetailFragment.back.setOnClickListener{
            redirect()
        }
    }

    private fun redirect() {
        findNavController().navigate(R.id.action_bookDetailFragment_to_bookListFragment)
    }


    override fun onDestroy() {
        super.onDestroy()
        _bookDetailsFragment = null
    }


}