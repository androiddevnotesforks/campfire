package com.pandulapeter.campfire.feature.shared.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import com.pandulapeter.campfire.R
import com.pandulapeter.campfire.data.persistence.PreferenceDatabase
import org.koin.android.ext.android.inject

abstract class BaseDialogFragment : AppCompatDialogFragment() {

    protected val onDialogItemSelectedListener get() = parentFragment as? OnDialogItemSelectedListener ?: activity as? OnDialogItemSelectedListener
    private val preferenceDatabase by inject<PreferenceDatabase>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        context?.let { context ->
            arguments?.let {
                return AlertDialog.Builder(context, if (preferenceDatabase.shouldUseDarkTheme) R.style.DarkAlertDialog else R.style.LightAlertDialog).createDialog(it)
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    abstract fun AlertDialog.Builder.createDialog(arguments: Bundle): AlertDialog

    interface OnDialogItemSelectedListener {

        fun onPositiveButtonSelected(id: Int)

        fun onNegativeButtonSelected(id: Int) = Unit
    }
}