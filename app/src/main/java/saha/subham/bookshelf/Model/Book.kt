package saha.subham.bookshelf.Model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import saha.subham.bookshelf.Database.Converters

@Parcelize
@Entity(tableName = "books")
@TypeConverters(Converters::class)
data class Book(
    val ISBN: String,
    val Notes: List<String>,
    val Pages: Int,
    val Publisher: String,
    val Title: String,
    val Year: Int,
    val created_at: String,
    val handle: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val villains: List<Villain>
) : Parcelable