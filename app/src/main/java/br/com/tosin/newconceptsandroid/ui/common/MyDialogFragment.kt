package br.com.tosin.newconceptsandroid.ui.common

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class MyDialogFragment: DialogFragment() {

    companion object {
        fun newInstance(title: String, message: String): MyDialogFragment {

            val extras = Bundle()
            extras.putString("TITLE", title)
            extras.putString("MESSAGE", message)

            val dialog = MyDialogFragment()
            dialog.arguments = extras
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString("TITLE", "")
        val msg = arguments?.getString("MESSAGE", "")

        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton("OK") { dialog, _ ->
            // User clicked OK button
            dialog.dismiss()
        }
        return builder.create()
    }
}
