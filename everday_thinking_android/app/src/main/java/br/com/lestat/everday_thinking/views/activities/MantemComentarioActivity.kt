package br.com.lestat.everday_thinking.views.activities

import androidx.appcompat.app.AppCompatActivity
import br.com.lestat.everday_thinking.database.SQLite
import android.os.Bundle
import br.com.lestat.everday_thinking.R
import br.com.lestat.everday_thinking.views.helper.Validadores
import br.com.lestat.everday_thinking.views.helper.Dialogs
import br.com.lestat.everday_thinking.views.helper.HideKeyBoard
import br.com.lestat.everday_thinking.views.helper.ExitPage
import br.com.lestat.everday_thinking.model.Comentario
import br.com.lestat.everday_thinking.views.helper.FormatarDataHorario
import br.com.lestat.everday_thinking.model.Usuario
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar

class MantemComentarioActivity : AppCompatActivity() {
    private var database: SQLite? = null
    private lateinit var editComentario: EditText

    private lateinit var btnCriarComentario: Button
    private lateinit var btnAlterarComentario: Button
    private lateinit var btnDeletarComentario: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantem_comentario)

        database = SQLite(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        ExitPage.mostrarSetaVoltar(toolbar, this)

        val toolbar_title = findViewById<TextView>(R.id.toolbar_title)
        toolbar_title.text = getText(R.string.comentario_title_criar)

        editComentario = findViewById(R.id.edit_comentario)
        btnCriarComentario = findViewById(R.id.btn_criar_comentario)
        btnAlterarComentario = findViewById(R.id.btn_alterar_comentario)
        btnDeletarComentario = findViewById(R.id.btn_deletar_comentario)

        btnCriarComentario.setOnClickListener {
            criarComentario()
        }

        btnAlterarComentario.setOnClickListener {
            alterarCometario()
        }

        btnDeletarComentario.setOnClickListener {
            deletarComentario()
        }

        val ll_botoes_alterar_deletar = findViewById<LinearLayout>(R.id.ll_botoes_alterar_deletar)

        if (comentario != null) {
            toolbar_title.text = getText(R.string.comentario_title_altera_deleta)
            editComentario.setText(comentario!!.comentario)
            btnCriarComentario.visibility = View.GONE
            ll_botoes_alterar_deletar.visibility = View.VISIBLE
        }

    }

    private fun verificaCampo(): Boolean {
        var valido = true
        val editComentarioTexto = editComentario.text.toString()
        if (!Validadores.vazio(editComentarioTexto)) {
            valido = false
            editComentario.error = getText(R.string.msg_erro_comentario).toString()
        }
        return valido
    }

    private fun criarComentario() {
        HideKeyBoard.hideKeyboard(this)
        if (verificaCampo()) {
            comentario = Comentario()
            comentario!!.idUsuario = usuario!!.id
            comentario!!.comentario = editComentario.text.toString()
            comentario!!.nome = usuario!!.nome
            comentario!!.data = FormatarDataHorario.formataDataHorario()
            database!!.addComment(comentario!!)
            finish()
        }
    }

    private fun deletarComentario() {
        database?.let { Dialogs.dialogDeletar(this, comentario, it) }
    }

    private fun alterarCometario() {
        HideKeyBoard.hideKeyboard(this)
        if (verificaCampo()) {
            comentario!!.comentario = editComentario.text.toString()
            comentario!!.data = FormatarDataHorario.formataDataHorario()
            database!!.updateComment(comentario!!)
            finish()
        }
    }

    companion object {
        @JvmField
        var usuario: Usuario? = null
        @JvmField
        var comentario: Comentario? = null
    }

}