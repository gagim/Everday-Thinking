package br.com.lestat.everday_thinking.views.helper

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object FormatarDataHorario {
    private const val formato = "dd/MM/yyyy HH:mm"
    @SuppressLint("SimpleDateFormat")
    fun formataDataHorario(): String {
        val data = Date()
        val dateFormat = SimpleDateFormat(formato)
        return dateFormat.format(data)
    }

    @SuppressLint("SimpleDateFormat")
    fun formataDataHorario(data: String): String {
        val dataFinal = data.split("T".toRegex()).toTypedArray()[0].replace("-".toRegex(), "/")
        val horario = data.split("T".toRegex()).toTypedArray()[1].replace("Z".toRegex(), "")
        return "$dataFinal ás $horario"
    }
}