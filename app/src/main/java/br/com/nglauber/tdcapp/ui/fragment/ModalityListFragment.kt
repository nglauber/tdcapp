package br.com.nglauber.tdcapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.presentation.ModalityListViewModel
import br.com.nglauber.tdcapp.presentation.model.ModalityBinding
import br.com.nglauber.tdcapp.ui.activity.SessionListActivity
import br.com.nglauber.tdcapp.ui.adapter.ModalityAdapter
import kotlinx.android.synthetic.main.activity_event_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ModalityListFragment : Fragment() {

    private val viewModel: ModalityListViewModel by sharedViewModel()

    private var eventId: Long = 0
    private var date: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventId = arguments?.getLong(EXTRA_EVENT_ID) ?: -1L
        date = arguments?.getString(EXTRA_DATE) ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_modality_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val state = viewModel.getState().value
        state?.let {
            context?.let {
                listView.adapter = ModalityAdapter(it, state.data?.get(date) ?: emptyList())
            }
        }
        listView.setOnItemClickListener { adapterView, _, i, _ ->
            val tdcModality = adapterView.adapter.getItem(i) as ModalityBinding
            context?.let {
                SessionListActivity.startActivity(it, eventId, tdcModality.id)
            }
        }
    }

    companion object {
        private const val EXTRA_EVENT_ID = "event_id"
        private const val EXTRA_DATE = "date"

        fun newInstance(eventId: Long, date: String): ModalityListFragment {
            return ModalityListFragment().apply {
                arguments = Bundle().apply {
                    putLong(EXTRA_EVENT_ID, eventId)
                    putString(EXTRA_DATE, date)
                }
            }
        }
    }
}
