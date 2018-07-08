package br.com.nglauber.tdcapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.presentation.AppViewModelFactory
import br.com.nglauber.tdcapp.presentation.ModalityListViewModel
import br.com.nglauber.tdcapp.presentation.model.ModalityBinding
import br.com.nglauber.tdcapp.ui.activity.SessionListActivity
import br.com.nglauber.tdcapp.ui.adapter.ModalityAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_event_list.*
import javax.inject.Inject

class ModalityListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    @Inject
    lateinit var viewModel: ModalityListViewModel

    private var eventId: Int = 0
    private var date: String = ""

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

        val sharedActivity = activity
        if (sharedActivity != null) {
            viewModel = ViewModelProviders.of(sharedActivity, viewModelFactory)
                    .get(ModalityListViewModel::class.java)
        } else {
            throw IllegalStateException("Activity is null")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventId = arguments?.getInt(EXTRA_EVENT_ID) ?: -1
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
            val tdcActivity = adapterView.adapter.getItem(i) as ModalityBinding
            context?.let {
                SessionListActivity.startActivity(it, eventId, tdcActivity.id)
            }
        }
    }

    companion object {
        private const val EXTRA_EVENT_ID = "event_id"
        private const val EXTRA_DATE = "date"

        fun newInstance(eventId: Int, date: String): ModalityListFragment {
            return ModalityListFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_EVENT_ID, eventId)
                    putString(EXTRA_DATE, date)
                }
            }
        }
    }
}
