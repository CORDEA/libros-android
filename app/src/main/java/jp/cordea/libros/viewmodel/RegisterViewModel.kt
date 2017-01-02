package jp.cordea.libros.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.support.v4.app.FragmentManager
import android.view.View
import com.google.zxing.integration.android.IntentIntegrator
import jp.cordea.libros.BR
import jp.cordea.libros.ErrorDialogFragment
import jp.cordea.libros.R
import jp.cordea.libros.api.LibrosClient
import jp.cordea.libros.api.response.BookResponse
import jp.cordea.libros.model.Book
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Yoshihiro Tanaka on 2016/12/31.
 */
class RegisterViewModel(private val context: Context,
                        private val fragmentManager: FragmentManager,
                        private val integrator: IntentIntegrator,
                        private val isEdit: Boolean,
                        @Bindable var book: Book) : BaseObservable() {

    var onSaveCompleted = { }

    val onScanClick = View.OnClickListener {
        integrator.setPrompt(context.resources.getString(R.string.barcode_prompt_message))
        integrator.initiateScan(IntentIntegrator.ONE_D_CODE_TYPES)
    }

    val onGetInfoClick = View.OnClickListener {
        readBookCode(book.code)
    }

    fun readBookCode(code: String) {
        LibrosClient.librosService
                .searchBook(code)
                .enqueue(object : Callback<BookResponse> {
                    override fun onResponse(call: Call<BookResponse>?, response: Response<BookResponse>?) {
                        response?.let {
                            if (response.body().status == 0) {
                                ErrorDialogFragment
                                        .newInstance(R.string.error_search_title, R.string.error_search_message)
                                        .show(fragmentManager, DialogKey)
                                return
                            }
                            book = response.body().book
                            notifyPropertyChanged(BR.book)
                        }
                    }

                    override fun onFailure(call: Call<BookResponse>?, t: Throwable?) {
                        ErrorDialogFragment
                            .newInstance(R.string.error_search_title, R.string.error_network_message)
                            .show(fragmentManager, DialogKey)
                    }
                })
    }

    val onSaveClick = View.OnClickListener {
        val service = LibrosClient.librosService
        if (isEdit) {
            service.patchBook(book)
                    .enqueue(getCallback())
        } else {
            service.postBook(book)
                    .enqueue(getCallback())
        }
    }

    private fun getCallback(): Callback<BookResponse> {
        var title = R.string.error_register_title
        if (isEdit) {
            title = R.string.error_update_title
        }
        return object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>?, response: Response<BookResponse>?) {
                response?.let {
                    if (response.body().status == 0) {
                        ErrorDialogFragment
                                .newInstance(title, R.string.error_validate_message)
                                .show(fragmentManager, DialogKey)
                        return
                    }
                    onSaveCompleted()
                }
            }

            override fun onFailure(call: Call<BookResponse>?, t: Throwable?) {
                ErrorDialogFragment
                        .newInstance(title, R.string.error_network_message)
                        .show(fragmentManager, DialogKey)
            }
        }
    }

    companion object {
        private val DialogKey = "Dialog"
    }
}