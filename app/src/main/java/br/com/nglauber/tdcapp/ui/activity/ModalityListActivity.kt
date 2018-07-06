package br.com.nglauber.tdcapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.repository.remote.model.TdcModality
import br.com.nglauber.tdcapp.repository.remote.service.TdcWebServiceFactory
import br.com.nglauber.tdcapp.ui.adapter.ModalitiesPagerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_modality_list.*

class ModalityListActivity : AppCompatActivity() {

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modality_list)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, -1)
        if (eventId == -1) {
            finish()
            return
        }
        fetchActivities(eventId)
    }

    private fun fetchActivities(eventId: Int) {
        val service = TdcWebServiceFactory().makeTdWebService(this, true)
        disposable = service.getModalitiesByEvent(eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { activityList ->
                    activityList.sortedWith(
                            compareBy({ it.date }, { it.positionOnEvent })
                    ).groupBy { it.date }
                }
                .subscribe(
                        { modalityByDate ->
                            handleSuccess(eventId, modalityByDate)
                        },
                        { e ->
                            handleError(e)
                        }
                )
    }

    private fun handleSuccess(eventId: Int, modalitiesByDate: Map<String, List<TdcModality>>) {
        viewPager.adapter = ModalitiesPagerAdapter(
                supportFragmentManager, eventId, modalitiesByDate
        )
        tabs.setupWithViewPager(viewPager)
    }

    private fun handleError(e: Throwable) {
        e.printStackTrace()
        Toast.makeText(this, R.string.error_loading_activities, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    companion object {
        const val EXTRA_EVENT_ID = "eventId"

        fun startActivity(context: Context, eventId: Int) {
            context.startActivity(Intent(context, ModalityListActivity::class.java).apply {
                putExtra(EXTRA_EVENT_ID, eventId)
            })
        }
    }
}
