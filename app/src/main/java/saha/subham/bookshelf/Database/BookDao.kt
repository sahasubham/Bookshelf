package saha.subham.bookshelf.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import retrofit2.http.DELETE
import saha.subham.bookshelf.Model.Book

@Dao
interface BookDao {

    /**Insert Records From API**/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books : List<Book>)
    /**All Records**/
    @Query("SELECT *  FROM books ORDER BY id DESC")
    suspend fun getBooks() : List<Book>
    /**Update Books Locally**/
    @Update
    suspend fun updateBooks(book: Book)
    /**INSERT Books Locally**/
    @Insert
    suspend fun addBooks(book: Book)
    /**Remove User**/
    @Delete
    suspend fun removeBook(book: Book)
    /**Specific Record**/
    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    suspend fun getBookById(id: Int): Book?
}