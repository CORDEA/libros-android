package jp.cordea.libros.api

import jp.cordea.libros.api.response.BookResponse
import jp.cordea.libros.api.response.Books
import jp.cordea.libros.api.response.SimpleResponse
import jp.cordea.libros.model.Book
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Yoshihiro Tanaka on 2017/01/01.
 */
interface LibrosService {

    @GET("/books")
    fun getBooks(): Call<Books>

    @GET("/search/book")
    fun searchBook(@Query("code") code: String): Call<BookResponse>

    @PATCH("/book")
    fun patchBook(@Body book: Book): Call<BookResponse>

    @DELETE("/book/{id}")
    fun deleteBook(@Path("id") id: Int): Call<SimpleResponse>

    @POST("/book")
    fun postBook(@Body book: Book): Call<BookResponse>

}