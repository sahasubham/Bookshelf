package saha.subham.bookshelf.Adapter

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import saha.subham.bookshelf.Fragment.BookListFragment
import saha.subham.bookshelf.Model.Book
import saha.subham.bookshelf.R
import saha.subham.bookshelf.Repository.BookRepository
import saha.subham.bookshelf.ViewModel.BookViewModel

class BookListAdapter(private var books: List<Book>, private val fragment: Fragment, private val bookViewModel: BookViewModel, private val context: Context) : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {


    fun updateData(newBooks: List<Book>) {
        books =  newBooks
        notifyDataSetChanged()
    }

    fun removeData(data: Book, position: Int) {
        bookViewModel.viewModelScope.launch {
            bookViewModel.removeBook(data)
            Toast.makeText(context, "Book Remove Successfully", Toast.LENGTH_SHORT).show()
            notifyItemRemoved(position)
        }
    }

    class ViewHolder(view: View)  : RecyclerView.ViewHolder(view) {
        private val bookName = view.findViewById<TextView>(R.id.bookName)
        private val publisherName = view.findViewById<TextView>(R.id.publisherName)
        private val publishYear = view.findViewById<TextView>(R.id.publishYear)

        fun bindData(data: Book) {
            bookName.text = data.Title
            publisherName.text = data.Publisher
            publishYear.text = " "+data.Year
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(books[position])
        val bundle = Bundle().apply {
            putParcelable("book", books[position])
        }
        /**Call Details**/
        holder.itemView.rootView.findViewById<CardView>(R.id.book).setOnClickListener {
            fragment.findNavController().navigate(R.id.action_bookListFragment_to_bookDetailFragment, bundle)
        }
        /**Call Edit**/
        holder.itemView.rootView.findViewById<CardView>(R.id.edit).setOnClickListener {
            fragment.findNavController().navigate(R.id.action_bookListFragment_to_addEditBookFragment, bundle)
        }
        /**Call Remove**/
        holder.itemView.rootView.findViewById<CardView>(R.id.remove).setOnClickListener {
            confirmation(books[position], position)

        }
    }

    private fun confirmation(data: Book, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton(context.resources.getString(R.string.yes)) {_, _ ->
            removeData(data, position)
        }
        builder.setNegativeButton(context.resources.getString(R.string.no)) {_,_ ->}
        builder.setTitle(context.resources.getString(R.string.are_you_sure))
        builder.setMessage(context.resources.getString(R.string.confirm_phase_one)+ " " + data.Title + " ?" + context.resources.getString(R.string.confirm_phase_two))
        builder.create().show()
    }
}