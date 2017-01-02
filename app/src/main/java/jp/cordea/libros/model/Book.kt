package jp.cordea.libros.model

import java.io.Serializable

/**
 * Created by Yoshihiro Tanaka on 2016/12/31.
 */
data class Book(
        val id: Int? = null,
        var title: String = "",
        var author: String = "",
        var publisher: String = "",
        var code: String = "") : Serializable
