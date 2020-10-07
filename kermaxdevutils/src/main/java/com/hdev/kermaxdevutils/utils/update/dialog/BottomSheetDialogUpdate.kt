package com.hdev.kermaxdevutils.utils.update.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hdev.kermaxdevutils.R
import com.hdev.kermaxdevutils.data.model.Update
import kotlinx.android.synthetic.main.layout_bottom_sheet_dialog_update.*

class BottomSheetDialogUpdate : RoundedBottomSheetDialog() {
    //initialize variable
    private var onButtonClick: OnButtonClick? = null
    private var data: Update? = null

    //set button click
    fun setOnButtonClick(onButtonClick: OnButtonClick) {
        this.onButtonClick = onButtonClick
    }

    //set data update (version name, version code, changelog)
    fun setData(data: Update) {
        this.data = data
    }

    //interface callback for button close or update app
    interface OnButtonClick {
        fun onButtonClose()
        fun onUpdate(link: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.layout_bottom_sheet_dialog_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (data != null) {
            val title = "Update ${data!!.versionName}"
            val changeLog = data!!.changelog

            text_view_update_title_version.text = title
            text_view_changelog.text = changeLog

            //action button close
            button_close.setOnClickListener {
                onButtonClick?.onButtonClose()
            }

            //action button update
            button_update.setOnClickListener {
                onButtonClick?.onUpdate(data!!.link)
            }
        }
    }
}