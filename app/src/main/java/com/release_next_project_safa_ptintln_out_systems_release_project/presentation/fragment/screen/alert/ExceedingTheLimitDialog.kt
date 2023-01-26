package com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.screen.alert

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object ExceedingTheLimitDialog {

    fun getAlert(
        context: Context, message: String,
        positiveButtonText: String
    ): AlertDialog.Builder {

        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setMessage(message)

        alertDialog.setPositiveButton(positiveButtonText, object: DialogInterface.OnClickListener {

            override fun onClick(dialog: DialogInterface?, which: Int) {

                /*
                * Something will happen here, but I'm too lazy to implement it, so let it be so;)
                * */

            }

        })

        alertDialog.create()
        return alertDialog

    }

}