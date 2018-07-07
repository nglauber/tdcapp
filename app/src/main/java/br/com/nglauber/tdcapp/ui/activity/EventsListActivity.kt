package br.com.nglauber.tdcapp.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.presentation.AppViewModelFactory
import br.com.nglauber.tdcapp.presentation.EventsListViewModel
import br.com.nglauber.tdcapp.presentation.ViewState
import br.com.nglauber.tdcapp.repository.model.Event
import br.com.nglauber.tdcapp.ui.adapter.EventAdapter
import kotlinx.android.synthetic.main.activity_event_list.*

class EventsListActivity : AppCompatActivity() {

    //TODO inject
    private val viewModel: EventsListViewModel by lazy {
        val factory = AppViewModelFactory(this.application)
        ViewModelProviders.of(this, factory).get(EventsListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModel.getState().observe(this, Observer { newState ->
            newState?.let {
                handleState(it)
            }
        })
        if (viewModel.getState().value == null) {
            viewModel.fetchEvents()
        }
    }

    private fun handleState(state: ViewState<List<Event>>) {
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

    private fun handleSuccess(eventList: List<Event>) {
        progressBar.visibility = View.GONE
        listView.adapter = EventAdapter(this, eventList)
        listView.setOnItemClickListener { adapterView, _, i, _ ->
            val event = adapterView.adapter.getItem(i) as Event
            ModalityListActivity.startActivity(this, event.id)
        }
    }

    private fun handleError(e: Throwable) {
        e.printStackTrace()
        progressBar.visibility = View.GONE
        Toast.makeText(this, R.string.error_loading_events, Toast.LENGTH_SHORT).show()
    }
}
