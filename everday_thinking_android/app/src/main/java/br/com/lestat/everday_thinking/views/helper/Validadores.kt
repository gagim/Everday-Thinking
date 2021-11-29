package br.com.lestat.everday_thinking.views.helper

import android.util.Patterns

object Validadores {
    fun vazio(valor: String?): Boolean {
        return valor != null && valor.isNotEmpty()
    }

    fun verificaEmail(email: String): Boolean {
        return if (vazio(email)) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else false
    }

    fun verificaSenha(senha: String, repetirSenha: String): Boolean {
        return if (vazio(senha) && vazio(repetirSenha)) {
            senha == repetirSenha
        } else false
    }
}