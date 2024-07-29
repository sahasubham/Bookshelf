package saha.subham.bookshelf.Api

import retrofit2.Response
import retrofit2.http.GET
import saha.subham.bookshelf.Model.Data

interface ApiInterface {

    @GET("books")
    suspend fun getBooks() : Response<Data>
}