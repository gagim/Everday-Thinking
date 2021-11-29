package br.com.lestat.everday_thinking.views.activities

import br.com.lestat.everday_thinking.views.helper.SalvarDadosLogado.recuperarDadosLogado
import br.com.lestat.everday_thinking.views.helper.Dialogs.dialogSair
import androidx.appcompat.app.AppCompatActivity
import br.com.lestat.everday_thinking.model.Usuario
import br.com.lestat.everday_thinking.model.Comentario
import br.com.lestat.everday_thinking.model.UltimasNovidades
import br.com.lestat.everday_thinking.database.SQLite
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import br.com.lestat.everday_thinking.R
import android.widget.TextView
import br.com.lestat.everday_thinking.services.ServiceBoticario
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import br.com.lestat.everday_thinking.views.fragments.TabFragmentAdapter
import br.com.lestat.everday_thinking.views.fragments.ComentariosFragment
import br.com.lestat.everday_thinking.views.fragments.NoticiasFragment
import java.util.ArrayList
import java.util.concurrent.ExecutionException

class HomeActivity : AppCompatActivity() {
    private var usuario: Usuario? = null
    private var comentarios: List<Comentario> = ArrayList()
    private var ultimasNovidades: UltimasNovidades? = null
    private var database: SQLite? = null

    override fun onResume() {
        super.onResume()
        comentarios = database!!.allComments
        initViewPager()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val emailUsuario = recuperarDadosLogado(this)
        database = SQLite(this)
        usuario = database!!.getUser(emailUsuario!!)
        comentarios = database!!.allComments

        val toobarTitle = findViewById<TextView>(R.id.toolbar_title)
        toobarTitle.text = "Bem vindo(a) " + usuario!!.nome

        val btn_sair = findViewById<ImageView>(R.id.btn_sair)
        btn_sair.visibility = View.VISIBLE
        btn_sair.setOnClickListener { dialogSair(this) }

        try {
            ultimasNovidades = ServiceBoticario().execute().get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        initViewPager()
    }

    private fun initViewPager() {
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.tablayout)
        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = TabFragmentAdapter(supportFragmentManager)
        adapter.addFrag(ComentariosFragment(this, usuario!!), "COMENTÁRIOS")
        adapter.addFrag(NoticiasFragment(this, ultimasNovidades), "NOTICIAS")
        ComentariosFragment.comentarios = comentarios
        viewPager.adapter = adapter
    }
}