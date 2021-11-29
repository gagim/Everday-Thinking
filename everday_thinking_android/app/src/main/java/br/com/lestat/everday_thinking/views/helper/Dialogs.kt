package br.com.lestat.everday_thinking.views.helper

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import br.com.lestat.everday_thinking.R
import br.com.lestat.everday_thinking.database.SQLite
import br.com.lestat.everday_thinking.model.Comentario

object Dialogs {
    var dialog: ProgressDialog? = null
    fun mostrarDialogCarregamento(context: Context?, msg: String?) {
        dialog = ProgressDialog(context)
        dialog!!.setMessage(msg)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(false)
        dialog!!.show()
    }

    fun esconderDialogCarregamento() {
        dialog!!.hide()
    }

    fun dialogSair(context: Activity) {
        val alertDialogBuilder = AlertDialog.Builder(
            context
        )
        alertDialogBuilder.setTitle(context.getString(R.string.sair))
        alertDialogBuilder
            .setMessage(context.getString(R.string.sair_mensagem))
            .setCancelable(false)
            .setPositiveButton("Sim") { _: DialogInterface?, _: Int ->
                SalvarDadosLogado.limparDadosLogado(
                    context
                )
            }
            .setNegativeButton("Não") { dialog: DialogInterface, _: Int -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun dialogDeletar(context: Activity, comentario: Comentario?, sqLite: SQLite) {
        val alertDialogBuilder = AlertDialog.Builder(
            context
        )
        alertDialogBuilder.setTitle(context.getString(R.string.deletar_comentario))
        alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("Sim") { _: DialogInterface?, id: Int ->
                sqLite.deleteComment(comentario!!)
                context.finish()
            }
            .setNegativeButton("Não") { dialog: DialogInterface, _: Int -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}