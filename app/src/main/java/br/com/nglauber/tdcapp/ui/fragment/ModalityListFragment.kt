package br.com.nglauber.tdcapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.repository.remote.model.TdcModality
import br.com.nglauber.tdcapp.ui.activity.SessionListActivity
import br.com.nglauber.tdcapp.ui.adapter.ModalityAdapter
import kotlinx.android.synthetic.main.activity_event_list.*

class ModalityListFragment : Fragment() {

    private var eventId: Int = 0
    private var modalityList: List<TdcModality> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_modality_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            listView.adapter = ModalityAdapter(it, modalityList)
        }
        listView.setOnItemClickListener { adapterView, _, i, _ ->
            val tdcActivity = adapterView.adapter.getItem(i) as TdcModality
            context?.let {
                SessionListActivity.startActivity(it, eventId, tdcActivity.id)
            }
        }
    }

    fun setSessionList(event: Int, modalities: List<TdcModality>) {
        this.eventId = event
        this.modalityList = modalities
        context?.let {
            listView.adapter = ModalityAdapter(it, modalities)
        }
    }
}
