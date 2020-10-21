package ru.samarin.prodev.utils

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class AlertDialogFragment : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity
        var alertDialog = getStubAlertDialog(context!!)
        val args = arguments
        if (args != null) {
            val title = args.getString(TITLE_KEY)
            val message = args.getString(MESSAGE_KEY)
            alertDialog = getAlertDialog(context, title, message)
        }
        return alertDialog
    }

    companion object {
        private const val TITLE_KEY = "7289720"
        private const val MESSAGE_KEY = "8927698412"

        fun newInstance(title: String?, message: String?): AlertDialogFragment {
            val dialogFragment = AlertDialogFragment()
            val args = Bundle()
            args.putString(TITLE_KEY, title)
            args.putString(MESSAGE_KEY, message)
            dialogFragment.arguments = args
            return dialogFragment
        }
    }
}