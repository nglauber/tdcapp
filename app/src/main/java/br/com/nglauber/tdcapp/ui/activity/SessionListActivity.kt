package br.com.nglauber.tdcapp.ui.activity

import android.app.Activity
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
import br.com.nglauber.tdcapp.ui.activity.ModalityListActivity.Companion.EXTRA_EVENT_ID
import br.com.nglauber.tdcapp.ui.adapter.SessionAdapter
import kotlinx.android.synthetic.main.activity_session_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SessionListActivity : AppCompatActivity() {

    private val viewModel: SessionListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_list)

        val eventId = intent.getLongExtra(EXTRA_EVENT_ID, -1L)
        val activityId = intent.getLongExtra(EXTRA_MODALITY_ID, -1L)
        if (eventId == -1L || activityId == -1L) {
            finish()
            return
        }
        lifecycle.addObserver(viewModel)
        observeSessions(eventId, activityId)
    }

    //TODO find a better way to refresh the list after bookmark an item
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == SessionActivity.REQUEST_CODE_EDIT) {
            viewModel.fetchSessionsByModality(
                    viewModel.eventId,
                    viewModel.modalityId
            )
        }
    }

    private fun observeSessions(eventId: Long, modalityId: Long) {
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

        fun startActivity(context: Context, eventId: Long, modalityId: Long) {
            context.startActivity(Intent(context, SessionListActivity::class.java).apply {
                putExtra(EXTRA_EVENT_ID, eventId)
                putExtra(EXTRA_MODALITY_ID, modalityId)
            })
        }
    }
}
