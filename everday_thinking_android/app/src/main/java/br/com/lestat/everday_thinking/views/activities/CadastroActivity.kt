package br.com.lestat.everday_thinking.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import br.com.lestat.everday_thinking.database.SQLite
import br.com.lestat.everday_thinking.model.Usuario
import android.os.Bundle
import android.widget.Button
import br.com.lestat.everday_thinking.R
import br.com.lestat.everday_thinking.views.helper.ExitPage
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import br.com.lestat.everday_thinking.views.helper.Validadores
import br.com.lestat.everday_thinking.views.helper.Criptografar
import br.com.lestat.everday_thinking.views.helper.HideKeyBoard
import br.com.lestat.everday_thinking.views.helper.DispararToast

class CadastroActivity : AppCompatActivity() {
    private var editNome: EditText? = null
    private var editEmail: EditText? = null
    private var editSenha: EditText? = null
    private var editRepetirSenha: EditText? = null
    private var btnCadastrar: Button? = null
    private var database: SQLite? = null
    private var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        ExitPage.mostrarSetaVoltar(toolbar, this)

        val toolbarTitle = findViewById<TextView>(R.id.toolbar_title)
        toolbarTitle.text = getText(R.string.cadastro_title)

        database = SQLite(this)
        editNome = findViewById(R.id.edit_nome)
        editEmail = findViewById(R.id.edit_email)
        editSenha = findViewById(R.id.edit_senha)
        editRepetirSenha = findViewById(R.id.edit_repetir_senha)

        btnCadastrar = findViewById(R.id.btn_cadastrar)

        btnCadastrar?.setOnClickListener {
            cadastrar()
        }

    }

    private fun verificaCampos(): Boolean {
        usuario = Usuario()
        var alertaErro: String? = null
        val nomeUsuario = editNome!!.text.toString()
        val emailUsuario = editEmail!!.text.toString()
        val senhaUsuario = editSenha!!.text.toString()
        val repetirSenha = editRepetirSenha!!.text.toString()

        if (nomeUsuario.isEmpty()) {
            alertaErro = getText(R.string.msg_erro_nome).toString()
            editNome!!.error = alertaErro
        }
        if (!Validadores.verificaEmail(emailUsuario)) {
            alertaErro = getText(R.string.msg_erro_email).toString()
            editEmail!!.error = alertaErro
        }
        if (!Validadores.verificaSenha(senhaUsuario, repetirSenha)) {
            alertaErro = getText(R.string.senha_incopativeis).toString()
            editSenha!!.error = alertaErro
            editRepetirSenha!!.error = alertaErro
        }

        val cripto = Criptografar.getInstancia(senhaUsuario)
        usuario!!.nome = nomeUsuario
        usuario!!.email = emailUsuario
        usuario!!.senha = cripto.senhaCriptografada

        return alertaErro == null
    }

    private fun cadastrar() {
        val camposvalidados = verificaCampos()
        HideKeyBoard.hideKeyboard(this)

        if (camposvalidados) {
            if (!usuario!!.email?.let { database!!.checkUser(it) }!!) {
                database!!.addUser(usuario!!)
                DispararToast.msgToast(this, R.string.sucesso_cadastro)
                finish()
            } else {
                DispararToast.msgToast(this, R.string.usuario_cadastrado)
            }
        }
    }

}