package br.com.lestat.everday_thinking.views.adapters

import br.com.lestat.everday_thinking.R
import android.widget.TextView

import android.content.Intent
import android.widget.LinearLayout
import br.com.lestat.everday_thinking.views.activities.MantemComentarioActivity
import br.com.lestat.everday_thinking.model.Comentario
import br.com.lestat.everday_thinking.model.Usuario
import android.annotation.SuppressLint
import android.app.Activity
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View

class ComentarioAdapter(
    private val activityContext: Activity,
    private val comentarios: List<Comentario>, private val usuario: Usuario
) : BaseAdapter() {
    class ViewHolder {
        var llComentario: LinearLayout? = null
        var txtNome: TextView? = null
        var txtData: TextView? = null
        var txtComentario: TextView? = null
    }

    override fun getCount(): Int {
        return comentarios.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(activityContext).inflate(
                R.layout.adapter_comentario, null
            )
            viewHolder = ViewHolder()
            viewHolder.llComentario = convertView
                .findViewById(R.id.ll_comentario)
            viewHolder.txtNome = convertView
                .findViewById(R.id.txt_nome)
            viewHolder.txtData = convertView
                .findViewById(R.id.txt_data)
            viewHolder.txtComentario = convertView.findViewById(R.id.txt_comentario)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        viewHolder.txtNome!!.text = comentarios[position].nome
        viewHolder.txtData!!.text = comentarios[position].data!!.replaceFirst(" ".toRegex(), " ás ")
        viewHolder.txtComentario!!.text = comentarios[position].comentario

        if (comentarios[position].idUsuario == usuario.id) {
            viewHolder.txtNome!!.text = activityContext.getString(R.string.author)
            viewHolder.llComentario!!.setOnClickListener {
                val manterComentario = Intent(activityContext, MantemComentarioActivity::class.java)
                MantemComentarioActivity.comentario = comentarios[position]
                MantemComentarioActivity.usuario = usuario
                activityContext.startActivity(manterComentario)
            }
        }

        return convertView!!
    }
}