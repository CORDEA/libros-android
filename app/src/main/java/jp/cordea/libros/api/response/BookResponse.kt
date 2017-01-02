package jp.cordea.libros.api.response

import jp.cordea.libros.model.Book

/**
 * Created by Yoshihiro Tanaka on 2017/01/01.
 */
data class BookResponse(val status: Int, val book: Book)