package br.com.nglauber.tdcapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.presentation.ModalityListViewModel
import br.com.nglauber.tdcapp.presentation.ViewState
import br.com.nglauber.tdcapp.presentation.model.ModalityBinding
import br.com.nglauber.tdcapp.ui.adapter.ModalitiesPagerAdapter
import kotlinx.android.synthetic.main.activity_modality_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ModalityListActivity : AppCompatActivity() {

    private val viewModel: ModalityListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modality_list)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val eventId = intent.getLongExtra(EXTRA_EVENT_ID, -1L)
        if (eventId == -1L) {
            finish()
            return
        }
        lifecycle.addObserver(viewModel)
        observerModalities(eventId)
    }

    private fun observerModalities(eventId: Long) {
        viewModel.eventId = eventId
        viewModel.getState().observe(this, Observer { newState ->
            newState?.let {
                handleState(eventId, it)
            }
        })
    }

    private fun handleState(eventId: Long, state: ViewState<Map<String, List<ModalityBinding>>>?) {
        when (state?.status) {
            ViewState.Status.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            ViewState.Status.SUCCESS -> {
                state.data?.let {
                    handleSuccess(eventId, it.keys.toList())
                }
            }
            ViewState.Status.ERROR -> {
                state.error?.let {
                    handleError(it)
                }
            }
        }
    }

    private fun handleSuccess(eventId: Long, modalityDates: List<String>) {
        progressBar.visibility = View.GONE
        viewPager.adapter = ModalitiesPagerAdapter(
                supportFragmentManager, eventId, modalityDates
        )
        tabs.setupWithViewPager(viewPager)
    }

    private fun handleError(e: Throwable) {
        e.printStackTrace()
        progressBar.visibility = View.GONE
        Toast.makeText(this, R.string.error_loading_activities, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_EVENT_ID = "eventId"

        fun startActivity(context: Context, eventId: Long) {
            context.startActivity(Intent(context, ModalityListActivity::class.java).apply {
                putExtra(EXTRA_EVENT_ID, eventId)
            })
        }
    }
}
