package br.com.nglauber.tdcapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.repository.model.Session
import kotlinx.android.synthetic.main.item_session.view.*

class SessionAdapter(context: Context, sessions: List<Session>) :
        ArrayAdapter<Session>(context, 0, sessions) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_session, parent, false)
        val session = getItem(position)
        view.txtTime.text = session.time
        view.txtTitle.text = session.title
        return view
    }
}