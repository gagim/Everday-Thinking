package br.com.lestat.everday_thinking.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.lestat.everday_thinking.R
import br.com.lestat.everday_thinking.views.helper.SalvarDadosLogado
import android.content.Intent
import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import android.os.Handler
import android.widget.*

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val emailUsuario = SalvarDadosLogado.recuperarDadosLogado(this)
        val gif = findViewById<ImageView>(R.id.gif)

        Glide.with(this)
            .load(R.drawable.brinks)
            .into(gif)

        val handle = Handler()
        if (emailUsuario != null) {
            if (emailUsuario.isEmpty()) {
                handle.postDelayed({ mostrarLogin() }, deley.toLong())
            } else {
                handle.postDelayed({ mostrarHome() }, deley.toLong())
            }
        }
    }

    private fun mostrarHome() {
        val intentHome = Intent(this, HomeActivity::class.java)
        startActivity(intentHome)
    }

    private fun mostrarLogin() {
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
    }

    companion object {
        private const val deley = 4000
    }
}