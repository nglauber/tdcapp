package br.com.nglauber.tdcapp.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.repository.remote.model.TdcSession
import br.com.nglauber.tdcapp.repository.remote.service.TdcWebServiceFactory
import br.com.nglauber.tdcapp.ui.adapter.SessionAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_event_list.*

class SessionListActivity : AppCompatActivity() {

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_list)

        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, -1)
        val activityId = intent.getIntExtra(EXTRA_MODALITY_ID, -1)
        if (eventId == -1 || activityId == -1) {
            finish()
            return
        }

        listView.setOnItemClickListener { adapterView, _, i, _ ->
            val session = adapterView.adapter.getItem(i) as TdcSession
            SessionActivity.startActivity(this, session, activityId, eventId)
        }
        fetchSessions(eventId, activityId)
    }

    private fun fetchSessions(eventId: Int, activityId: Int) {
        val service = TdcWebServiceFactory().makeTdWebService(this, true)
        disposable = service.getSessionsByModality(eventId, activityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { sessionList -> sessionList.sortedBy { it.time } }
                .subscribe(
                        { sessionList ->
                            handleSuccess(sessionList)
                        },
                        { e ->
                            handleError(e)
                        }
                )
    }

    private fun handleSuccess(sessions: List<TdcSession>) {
        listView.adapter = SessionAdapter(this, sessions)
    }

    private fun handleError(e: Throwable) {
        e.printStackTrace()
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
