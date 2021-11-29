package br.com.lestat.everday_thinking.views.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import br.com.lestat.everday_thinking.database.SQLite
import android.os.Bundle
import br.com.lestat.everday_thinking.R
import android.widget.TextView
import android.text.Html
import br.com.lestat.everday_thinking.views.helper.Validadores
import br.com.lestat.everday_thinking.views.helper.Criptografar
import br.com.lestat.everday_thinking.views.helper.HideKeyBoard
import br.com.lestat.everday_thinking.views.helper.SalvarDadosLogado
import android.content.Intent
import android.widget.Button
import br.com.lestat.everday_thinking.views.helper.DispararToast

class LoginActivity : AppCompatActivity() {
    private var editEmail: EditText? = null
    private var editSenha: EditText? = null
    private var btnAcessar: Button? = null
    private var txtCadastro: TextView? = null
    private var database: SQLite? = null
    private var emailUsuario: String? = null
    private var senhaUsuario: String? = null

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        database = SQLite(this)

        editEmail = findViewById(R.id.edit_email)
        editSenha = findViewById(R.id.edit_senha)
        btnAcessar = findViewById(R.id.btn_acessar)
        txtCadastro = findViewById(R.id.txt_cadastro)

        val txt_cadastro = findViewById<TextView>(R.id.txt_cadastro)
        txt_cadastro.text = Html.fromHtml("Cadastre-se <b>Aqui!</b>")

        btnAcessar?.setOnClickListener {
            acessar()
        }

        txt_cadastro?.setOnClickListener {
            cadastro()
        }
    }

    private fun validaCampos(): Boolean {
        var valido = true
        emailUsuario = editEmail!!.text.toString()
        senhaUsuario = editSenha!!.text.toString()
        if (!Validadores.verificaEmail(emailUsuario!!)) {
            valido = false
            editEmail!!.error = getText(R.string.msg_erro_email).toString()
        }
        if (!Validadores.vazio(senhaUsuario)) {
            valido = false
            editSenha!!.error = getText(R.string.msg_erro_senha).toString()
        }
        return valido
    }

    private fun acessar() {
        if (validaCampos()) {
            val cripto = senhaUsuario?.let { Criptografar.getInstancia(it) }
            val senhaCriptografada = cripto?.senhaCriptografada
            if (senhaCriptografada?.let { database!!.checkUser(emailUsuario!!, it) } == true) {
                HideKeyBoard.hideKeyboard(this)
                SalvarDadosLogado.salvarDadosLogado(this, emailUsuario)
                val home = Intent(this, HomeActivity::class.java)
                finish()
                startActivity(home)
            } else {
                editEmail!!.setText("")
                editSenha!!.setText("")
                DispararToast.msgToast(this, R.string.usuario_nao_encontrado)
            }
        }
    }

    private fun cadastro() {
        val cadastro = Intent(this, CadastroActivity::class.java)
        startActivity(cadastro)
    }

}