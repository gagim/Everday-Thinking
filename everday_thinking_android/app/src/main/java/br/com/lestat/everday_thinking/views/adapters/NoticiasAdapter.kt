package br.com.lestat.everday_thinking.views.adapters

import br.com.lestat.everday_thinking.R
import br.com.lestat.everday_thinking.views.helper.FormatarDataHorario
import android.annotation.SuppressLint
import android.app.Activity
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import br.com.lestat.everday_thinking.model.UltimasNovidades
import com.squareup.picasso.Picasso

class NoticiasAdapter(
    private val activityContext: Activity,
    private val ultimasNovidades: UltimasNovidades
) : BaseAdapter() {
    class ViewHolder {
        var txtMensagem: TextView? = null
        var imgNoticia: ImageView? = null
        var txtData: TextView? = null
    }

    override fun getCount(): Int {
        return ultimasNovidades.news!!.size
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
                R.layout.adapter_noticias, null
            )
            viewHolder = ViewHolder()
            viewHolder.txtMensagem = convertView
                .findViewById(R.id.txt_mensagem)
            viewHolder.imgNoticia = convertView.findViewById(R.id.img_noticia)
            viewHolder.txtData = convertView
                .findViewById(R.id.txt_data)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        val data =
            FormatarDataHorario.formataDataHorario(ultimasNovidades.news!![position].message!!.created_at!!)
        viewHolder.txtMensagem!!.text = ultimasNovidades.news!![position].message!!.content
        viewHolder.txtData!!.text = data
        Picasso.with(activityContext)
            .load(ultimasNovidades.news!![position].user!!.profile_picture)
            .into(viewHolder.imgNoticia)
        return convertView!!
    }
}