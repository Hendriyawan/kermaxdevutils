package com.hdev.kermaxdevutils.dialog

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hdev.kermaxdevutils.R

open class RoundedBottomSheetDialog : BottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setCancelable(false)
        return dialog
    }
}