package br.com.lestat.everday_thinking.views.helper

import android.app.Activity
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.content.Intent
import br.com.lestat.everday_thinking.views.activities.SplashScreenActivity

object SalvarDadosLogado {
    private lateinit var sharedPref: SharedPreferences
    private const val emailKey = "chaveEmail"
    fun salvarDadosLogado(activity: Activity?, email: String?) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = sharedPref.edit()
        editor.putString(emailKey, email)
        editor.apply()
    }

    fun recuperarDadosLogado(activity: Activity?): String? {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        return sharedPref.getString(emailKey, "")
    }

    @SuppressLint("CommitPrefEdits")
    fun limparDadosLogado(activity: Activity) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
        activity.finish()
        val login = Intent(activity, SplashScreenActivity::class.java)
        activity.startActivity(login)
    }
}