package br.com.nglauber.tdcapp.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.presentation.EventsListViewModel
import br.com.nglauber.tdcapp.presentation.ViewState
import br.com.nglauber.tdcapp.presentation.model.EventBiding
import br.com.nglauber.tdcapp.ui.adapter.EventAdapter
import kotlinx.android.synthetic.main.activity_event_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventsListActivity : AppCompatActivity() {

    private val viewModel: EventsListViewModel  by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        lifecycle.addObserver(viewModel)
        observeEvents()
    }

    private fun observeEvents() {
        viewModel.getState().observe(this, Observer { newState ->
            newState?.let {
                handleState(it)
            }
        })
    }

    private fun handleState(state: ViewState<List<EventBiding>>) {
        when (state.status) {
            ViewState.Status.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            ViewState.Status.SUCCESS -> {
                state.data?.let {
                    handleSuccess(it)
                }
            }
            ViewState.Status.ERROR -> {
                state.error?.let {
                    handleError(it)
                }
            }
        }
    }

    private fun handleSuccess(eventList: List<EventBiding>) {
        progressBar.visibility = View.GONE
        listView.adapter = EventAdapter(this, eventList)
        listView.setOnItemClickListener { adapterView, _, i, _ ->
            val event = adapterView.adapter.getItem(i) as EventBiding
            ModalityListActivity.startActivity(this, event.id)
        }
    }

    private fun handleError(e: Throwable) {
        e.printStackTrace()
        progressBar.visibility = View.GONE
        Toast.makeText(this, R.string.error_loading_events, Toast.LENGTH_SHORT).show()
    }
}
