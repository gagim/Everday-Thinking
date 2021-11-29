package br.com.lestat.everday_thinking.views.fragments

import android.app.Activity
import br.com.lestat.everday_thinking.model.Usuario
import br.com.lestat.everday_thinking.views.adapters.ComentarioAdapter
import android.annotation.SuppressLint
import android.widget.LinearLayout
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import br.com.lestat.everday_thinking.R
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import br.com.lestat.everday_thinking.views.activities.MantemComentarioActivity
import br.com.lestat.everday_thinking.model.Comentario

class ComentariosFragment(private val activityContext: Activity, private val usuario: Usuario) :
    Fragment() {
    private var adapter: ComentarioAdapter? = null

    @SuppressLint("StaticFieldLeak")
    class ViewHolder {
        lateinit var llComentarios: LinearLayout
        lateinit var txtMensagem: TextView
        lateinit var btnCriarComentario: Button

        companion object {
            lateinit var lsItens: ListView
        }
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val convertView = inflater.inflate(R.layout.fragment_comentarios, container, false)
        val viewHolder = ViewHolder()

        viewHolder.llComentarios = convertView.findViewById(R.id.ll_comentarios)
        ViewHolder.lsItens = convertView.findViewById(R.id.ls_itens)
        viewHolder.txtMensagem = convertView.findViewById(R.id.txt_mensagem)
        viewHolder.btnCriarComentario = convertView.findViewById(R.id.btn_criar_comentario)

        if (comentarios!!.isNotEmpty()) {
            adapter = ComentarioAdapter(activityContext, comentarios!!, usuario)
            adapter!!.notifyDataSetInvalidated()
            adapter!!.notifyDataSetChanged()
            ViewHolder.lsItens.adapter = adapter
        } else {
            viewHolder.txtMensagem.visibility = View.VISIBLE
        }

        viewHolder.btnCriarComentario.setOnClickListener {
            val manterComentario = Intent(activityContext, MantemComentarioActivity::class.java)
            MantemComentarioActivity.comentario = null
            MantemComentarioActivity.usuario = usuario
            activityContext.startActivity(manterComentario)
        }

        convertView.tag = viewHolder
        return convertView
    }

    companion object {
        var comentarios: List<Comentario>? = null
    }
}