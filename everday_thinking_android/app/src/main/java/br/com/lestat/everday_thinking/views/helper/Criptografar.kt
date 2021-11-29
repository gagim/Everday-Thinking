package br.com.lestat.everday_thinking.views.helper

import java.lang.StringBuilder
import java.util.HashMap

class Criptografar private constructor() {
    private val alfa = CharArray(52)
    private val nume = CharArray(10)
    private val letras = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ"
    private val numeros = "0123456789"
    private val valores = HashMap<Char, String>()
    private var senha = "senha"

    val senhaCriptografada: String
        get() {
            val sb = StringBuilder()
            for (element in senha) {
                sb.append(valores[element])
            }
            return sb.toString()
        }

    private fun setSenhaOriginal(senha: String) {
        this.senha = senha
        for (i in 0..51) {
            if (i % 2 == 0) { // minúsculas
                valores[alfa[i]] = String.format("%02X", logica(senha.length.toLong() * i % 2014))
            } else { // maiúsculas
                valores[alfa[i]] = String.format("%02x", logica(senha.length.toLong() * i % 2013))
            }
        }
        for (i in 0..9) {
            if (i % 2 == 0) { // pares
                valores[nume[i]] = String.format("%02X", logica(senha.length.toLong() * i % 2012))
            } else { // ímpares
                valores[nume[i]] = String.format("%02x", logica(senha.length.toLong() * i % 2011))
            }
        }
    }

    private fun logica(n: Long): Long {
        val cubo = n * n * n
        val x = cubo + 157
        val y = cubo * n * (21 * 2007)
        return 2 + x + y
    }

    companion object {
        private val criptografia = Criptografar()
        fun getInstancia(senha: String): Criptografar {
            criptografia.setSenhaOriginal(senha)
            return criptografia
        }
    }

    init {
        for (i in 0..51) {
            alfa[i] = letras[i]
        }
        for (i in 0..9) {
            nume[i] = numeros[i]
        }
    }
}