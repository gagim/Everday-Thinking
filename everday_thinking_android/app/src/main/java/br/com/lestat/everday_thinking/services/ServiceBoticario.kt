package br.com.lestat.everday_thinking.services

import android.os.AsyncTask
import br.com.lestat.everday_thinking.model.UltimasNovidades
import com.google.gson.Gson
import java.io.IOException
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class ServiceBoticario : AsyncTask<Void?, Void?, UltimasNovidades>() {
    override fun doInBackground(vararg p0: Void?): UltimasNovidades? {
        val resposta = StringBuilder()
        try {
            val url = URL("https://gb-mobile-app-teste.s3.amazonaws.com/data.json")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Content-type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.doOutput = true
            connection.connectTimeout = 5000
            connection.connect()
            val scanner = Scanner(url.openStream())
            while (scanner.hasNext()) {
                resposta.append(scanner.nextLine())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Gson().fromJson(resposta.toString(), UltimasNovidades::class.java)
    }
}