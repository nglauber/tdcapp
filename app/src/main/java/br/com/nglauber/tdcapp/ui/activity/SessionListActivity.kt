package br.com.nglauber.tdcapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.presentation.SessionListViewModel
import br.com.nglauber.tdcapp.presentation.ViewState
import br.com.nglauber.tdcapp.presentation.model.SessionBinding
import br.com.nglauber.tdcapp.ui.adapter.SessionAdapter
import kotlinx.android.synthetic.main.activity_session_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SessionListActivity : AppCompatActivity() {


    private val viewModel: SessionListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_list)

        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, -1)
        val activityId = intent.getIntExtra(EXTRA_MODALITY_ID, -1)
        if (eventId == -1 || activityId == -1) {
            finish()
            return
        }
        lifecycle.addObserver(viewModel)
        observeSessions(eventId, activityId)
    }

    private fun observeSessions(eventId: Int, modalityId: Int) {
        viewModel.eventId = eventId
        viewModel.modalityId = modalityId
        viewModel.getState().observe(this, Observer { newState ->
            newState?.let {
                handleState(it)
            }
        })
    }

    private fun handleState(state: ViewState<List<SessionBinding>>?) {
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

    private fun handleSuccess(sessions: List<SessionBinding>) {
        progressBar.visibility = View.GONE
        listView.adapter = SessionAdapter(this, sessions)
        listView.setOnItemClickListener { adapterView, _, i, _ ->
            val session = adapterView.adapter.getItem(i) as SessionBinding
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
