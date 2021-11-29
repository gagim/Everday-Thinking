package br.com.lestat.everday_thinking.views.helper

import android.content.Context
import android.widget.Toast

object DispararToast {
    fun msgToast(context: Context, msg: Int) {
        Toast.makeText(context, context.getText(msg).toString(), Toast.LENGTH_LONG).show()
    }
}