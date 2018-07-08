package br.com.nglauber.tdcapp.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.com.nglauber.tdcapp.presentation.model.EventBiding

class EventAdapter(context: Context, events: List<EventBiding>)
    : ArrayAdapter<EventBiding>(context, android.R.layout.simple_list_item_1, events) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView = super.getView(position, convertView, parent) as TextView
        textView.text = getItem(position).description
        return textView
    }
}