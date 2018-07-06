package br.com.nglauber.tdcapp.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.repository.remote.model.TdcEvent
import br.com.nglauber.tdcapp.repository.remote.service.TdcWebServiceFactory
import br.com.nglauber.tdcapp.ui.adapter.EventAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_event_list.*

class EventsListActivity : AppCompatActivity() {

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        listView.setOnItemClickListener { adapterView, _, i, _ ->
            val event = adapterView.adapter.getItem(i) as TdcEvent
            ModalityListActivity.startActivity(this, event.id)
        }
        fetchEvents()
    }

    private fun fetchEvents() {
        val service = TdcWebServiceFactory().makeTdWebService(this, true)
        disposable = service.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { eventList -> eventList.sortedByDescending { it.id } }
                .subscribe(
                        { eventList ->
                            handleSuccess(eventList)
                        },
                        { e ->
                            handleError(e)
                        }
                )
    }

    private fun handleSuccess(eventList: List<TdcEvent>) {
        listView.adapter = EventAdapter(this, eventList)
    }

    private fun handleError(e: Throwable) {
        e.printStackTrace()
        Toast.makeText(this, R.string.error_loading_events, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
