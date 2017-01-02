package jp.cordea.libros

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

/**
 * Created by Yoshihiro Tanaka on 2017/01/02.
 */
class ErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
                .setTitle(arguments.getInt(TitleKey))
                .setMessage(arguments.getInt(MessageKey))
                .setPositiveButton(R.string.close, null)
                .create()
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    companion object {
        private val TitleKey = "Title"
        private val MessageKey = "Message"

        fun newInstance(title: Int, message: Int): ErrorDialogFragment {
            return ErrorDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(TitleKey, title)
                    putInt(MessageKey, message)
                }
            }
        }
    }
}