package br.com.nglauber.tdcapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.presentation.AppViewModelFactory
import br.com.nglauber.tdcapp.presentation.SessionListViewModel
import br.com.nglauber.tdcapp.presentation.ViewState
import br.com.nglauber.tdcapp.repository.model.Session
import br.com.nglauber.tdcapp.ui.adapter.SessionAdapter
import kotlinx.android.synthetic.main.activity_session_list.*

class SessionListActivity : AppCompatActivity() {

    //TODO inject
    private val viewModel: SessionListViewModel by lazy {
        val factory = AppViewModelFactory(this.application)
        ViewModelProviders.of(this, factory).get(SessionListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_list)

        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, -1)
        val activityId = intent.getIntExtra(EXTRA_MODALITY_ID, -1)
        if (eventId == -1 || activityId == -1) {
            finish()
            return
        }
        fetchSessions(eventId, activityId)
    }

    private fun fetchSessions(eventId: Int, modalityId: Int) {
        viewModel.getState().observe(this, Observer { newState ->
            newState?.let {
                handleState(it)
            }
        })
        if (viewModel.getState().value == null) {
            viewModel.fetchSessionsByModality(eventId, modalityId)
        }
    }

    private fun handleState(state: ViewState<List<Session>>?) {
        when (state?.status) {
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

    private fun handleSuccess(sessions: List<Session>) {
        progressBar.visibility = View.GONE
        listView.adapter = SessionAdapter(this, sessions)
        listView.setOnItemClickListener { adapterView, _, i, _ ->
            val session = adapterView.adapter.getItem(i) as Session
            SessionActivity.startActivity(this, viewModel.eventId, viewModel.modalityId, session)
        }
    }

    private fun handleError(e: Throwable) {
        e.printStackTrace()
        progressBar.visibility = View.GONE
        Toast.makeText(this, R.string.error_loading_sessions, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val EXTRA_EVENT_ID = "eventId"
        private const val EXTRA_MODALITY_ID = "activityId"

        fun startActivity(context: Context, eventId: Int, modalityId: Int) {
            context.startActivity(Intent(context, SessionListActivity::class.java).apply {
                putExtra(EXTRA_EVENT_ID, eventId)
                putExtra(EXTRA_MODALITY_ID, modalityId)
            })
        }
    }
}
