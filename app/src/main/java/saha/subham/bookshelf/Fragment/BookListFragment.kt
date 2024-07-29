package saha.subham.bookshelf.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import saha.subham.bookshelf.Adapter.BookListAdapter
import saha.subham.bookshelf.Model.Book
import saha.subham.bookshelf.MyApplication
import saha.subham.bookshelf.R
import saha.subham.bookshelf.ViewModel.BookViewModel
import saha.subham.bookshelf.ViewModel.BooksViewModelFactory
import saha.subham.bookshelf.databinding.FragmentBookListBinding

class BookListFragment : Fragment() {

    private var _bookListingFragment : FragmentBookListBinding? = null
    private val bookListFragment get() = _bookListingFragment!!
    private lateinit var bookListAdapter: BookListAdapter
    private lateinit var bookViewModel: BookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callData()
    }

    private fun callData() {
        val repository = (requireActivity().application as MyApplication).bookRepository

        bookViewModel = ViewModelProvider(this, BooksViewModelFactory(repository))[BookViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bookListingFragment = FragmentBookListBinding.inflate(inflater, container, false)

        bookListFragment.progressLayout.visibility = View.VISIBLE
        bookListFragment.noDataLayout.visibility = View.GONE
        bindData()

        return bookListFragment.root
    }

    private fun bindData() {
        bookViewModel.books.observe(viewLifecycleOwner) {
            if(it != null) {
                bookListAdapter = BookListAdapter(it.data, this, bookViewModel, requireContext())
                bookListFragment.bookList.layoutManager = LinearLayoutManager(context)
                bookListFragment.bookList.adapter = bookListAdapter
                bookListAdapter.updateData(it.data)
                bookListFragment.progressLayout.visibility = View.GONE
                if(it.data.isNotEmpty()) {
                    bookListFragment.noDataLayout.visibility = View.GONE
                } else {
                    bookListFragment.noDataLayout.visibility = View.VISIBLE
                }
            } else {
                bookListFragment.progressLayout.visibility = View.GONE
                bookListFragment.noDataLayout.visibility = View.VISIBLE
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookListFragment.addEdit.setOnClickListener{
            findNavController().navigate(R.id.action_bookListFragment_to_addEditBookFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bookListingFragment = null
    }


}