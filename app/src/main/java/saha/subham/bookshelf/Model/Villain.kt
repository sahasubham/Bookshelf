package saha.subham.bookshelf.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Villain(
    val name: String,
    val url: String
) : Parcelable