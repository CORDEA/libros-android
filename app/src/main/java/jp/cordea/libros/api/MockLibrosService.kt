package jp.cordea.libros.api

import jp.cordea.libros.api.response.BookResponse
import jp.cordea.libros.api.response.Books
import jp.cordea.libros.api.response.SimpleResponse
import jp.cordea.libros.model.Book
import retrofit2.Call
import retrofit2.mock.BehaviorDelegate

/**
 * Created by Yoshihiro Tanaka on 2017/01/01.
 */
class MockLibrosService(private val delegate: BehaviorDelegate<LibrosService>) : LibrosService {

    override fun getBooks(): Call<Books> {
        val books = (0..10).map {
            Book(it,
                    "book%d".format(it),
                    "author%d".format(it),
                    "publisher%d".format(it),
                    "0000000000000")
        }
        return delegate.returningResponse(Books(1, books)).getBooks()
    }

    override fun patchBook(book: Book): Call<BookResponse> {
        return delegate
                .returningResponse(BookResponse(1, Book()))
                .patchBook(book)
    }

    override fun deleteBook(id: Int): Call<SimpleResponse> {
        return delegate.returningResponse(SimpleResponse(1)).deleteBook(id)
    }

    override fun postBook(book: Book): Call<BookResponse> {
        return delegate
                .returningResponse(BookResponse(1, Book()))
                .postBook(book)
    }

    override fun searchBook(code: String): Call<BookResponse> {
        return delegate
                .returningResponse(BookResponse(1,
                        Book(0,"book", "author", "publisher", "0000000000000")))
                .searchBook(code)
    }

}