package jp.cordea.libros

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import jp.cordea.libros.model.Book
import jp.cordea.libros.viewmodel.SelectActionViewModel

/**
 * Created by Yoshihiro Tanaka on 2017/01/01.
 */
class SelectActionDialogFragment : DialogFragment() {

    var onDelete: (Int) -> Unit = { }

    private val viewModel by lazy {
        SelectActionViewModel(context, onDelete)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val book = arguments.getSerializable(BookKey) as Book
        return AlertDialog.Builder(context)
                .setItems(R.array.select_actions, { dialog, which ->
                    if (which == 0) {
                        viewModel.editOnClick(book)
                    } else {
                        viewModel.deleteOnClick(book.id)
                    }
                    dismiss()
                })
                .create()
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    companion object {
        private val BookKey = "Book"

        fun newInstance(book: Book): SelectActionDialogFragment {
            return SelectActionDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BookKey, book)
                }
            }
        }
    }
}
