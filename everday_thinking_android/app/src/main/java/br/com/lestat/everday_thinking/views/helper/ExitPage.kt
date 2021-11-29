package br.com.lestat.everday_thinking.views.helper

import android.app.Activity
import android.view.View
import androidx.appcompat.widget.Toolbar
import br.com.lestat.everday_thinking.R

object ExitPage {
    fun mostrarSetaVoltar(toolbar: Toolbar, activity: Activity) {
        toolbar.setNavigationIcon(R.drawable.ic_seta_esquerda)
        toolbar.setNavigationOnClickListener { v: View? ->
            HideKeyBoard.hideKeyboard(activity)
            activity.finish()
        }
    }

}