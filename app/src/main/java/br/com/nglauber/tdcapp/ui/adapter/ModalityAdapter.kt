package br.com.nglauber.tdcapp.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.com.nglauber.tdcapp.repository.remote.model.TdcModality

class ModalityAdapter(context: Context, modalities: List<TdcModality>) :
        ArrayAdapter<TdcModality>(context, android.R.layout.simple_list_item_1, modalities) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val activity = getItem(position)
        val textView = super.getView(position, convertView, parent) as TextView
        textView.text = activity.description
        return textView
    }
}