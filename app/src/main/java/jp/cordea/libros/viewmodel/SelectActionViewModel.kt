package jp.cordea.libros.viewmodel

import android.content.Context
import jp.cordea.libros.RegisterActivity
import jp.cordea.libros.api.LibrosClient
import jp.cordea.libros.api.response.SimpleResponse
import jp.cordea.libros.model.Book
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Yoshihiro Tanaka on 2017/01/01.
 */
class SelectActionViewModel(private val context: Context, private val onDelete: (Int) -> Unit) {

    val editOnClick: (Book) -> Unit = {
        context.startActivity(RegisterActivity.createIntent(context, true, it))
    }

    val deleteOnClick: (Int?) -> Unit  = { it ->
        it?.let {
            LibrosClient.librosService
                    .deleteBook(it)
                    .enqueue(object : Callback<SimpleResponse> {
                        override fun onResponse(call: Call<SimpleResponse>?, response: Response<SimpleResponse>?) {
                            response?.let { res ->
                                onDelete(it)
                            }
                        }

                        override fun onFailure(call: Call<SimpleResponse>?, t: Throwable?) {
                        }
                    })
        }
    }
}