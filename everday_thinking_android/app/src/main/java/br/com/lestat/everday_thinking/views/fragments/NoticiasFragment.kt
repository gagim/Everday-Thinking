package br.com.lestat.everday_thinking.views.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.lestat.everday_thinking.R
import br.com.lestat.everday_thinking.model.UltimasNovidades
import br.com.lestat.everday_thinking.views.adapters.NoticiasAdapter

class NoticiasFragment(
    private val activityContext: Activity,
    private val ultimasNovidades: UltimasNovidades?
) : Fragment() {
    class ViewHolder {
        lateinit var lsItens: ListView
        lateinit var txtMensagem: TextView
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val convertView = inflater.inflate(R.layout.fragment_noticias, container, false)
        val viewHolder = ViewHolder()

        viewHolder.lsItens = convertView.findViewById(R.id.ls_itens)
        viewHolder.txtMensagem = convertView.findViewById(R.id.txt_mensagem)

        if (ultimasNovidades != null) {
            val adapter = NoticiasAdapter(activityContext, ultimasNovidades)
            viewHolder.lsItens.adapter = adapter
        } else {
            viewHolder.txtMensagem.visibility = View.VISIBLE
        }
        convertView.tag = viewHolder

        return convertView
    }
}