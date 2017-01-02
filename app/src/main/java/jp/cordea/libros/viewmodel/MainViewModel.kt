package jp.cordea.libros.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.AdapterView
import jp.cordea.libros.BR
import jp.cordea.libros.R
import jp.cordea.libros.RegisterActivity
import jp.cordea.libros.SelectActionDialogFragment
import jp.cordea.libros.adapter.MainListAdapter
import jp.cordea.libros.api.LibrosClient
import jp.cordea.libros.api.response.Books
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Yoshihiro Tanaka on 2016/12/31.
 */
class MainViewModel(private val context: Context, private val manager: FragmentManager) : BaseObservable() {

    val adapter = MainListAdapter(context, R.layout.list_item_main)

    @Bindable
    var progressVisibility = View.VISIBLE
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisibility)
        }

    @Bindable
    var errorVisibility = View.GONE
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorVisibility)
        }

    val onClick = View.OnClickListener {
        context.startActivity(RegisterActivity.createIntent(context, false))
    }

    val retryOnClick = View.OnClickListener {
        refreshItems()
    }

    val onItemLongClick = AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
        val dialog = SelectActionDialogFragment.newInstance(adapter.getItem(i).book)
        dialog.onDelete = { id ->
            val items = adapter.items.filter { it.book.id != id }
            adapter.items = items
        }
        dialog.show(manager, Tag)
        true
    }

    fun refreshItems() {
        errorVisibility = View.GONE
        progressVisibility = View.VISIBLE
        LibrosClient.librosService
                .getBooks()
                .enqueue(object : Callback<Books> {
                    override fun onResponse(call: Call<Books>?, response: Response<Books>?) {
                        response?.let {
                            adapter.items = it.body().books.map(::MainListItemViewModel)
                            progressVisibility = View.GONE
                            return
                        }
                        errorVisibility = View.VISIBLE
                        progressVisibility = View.GONE
                    }

                    override fun onFailure(call: Call<Books>?, t: Throwable?) {
                        errorVisibility = View.VISIBLE
                        progressVisibility = View.GONE
                    }
                })
    }

    companion object {
        private val Tag = "dialog"
    }

}