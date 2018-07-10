package br.com.nglauber.tdcapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.presentation.AppViewModelFactory
import br.com.nglauber.tdcapp.presentation.ModalityListViewModel
import br.com.nglauber.tdcapp.presentation.ViewState
import br.com.nglauber.tdcapp.presentation.model.ModalityBinding
import br.com.nglauber.tdcapp.ui.adapter.ModalitiesPagerAdapter
import dagger.android.AndroidInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_modality_list.*
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector
import dagger.android.AndroidInjector


class ModalityListActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    @Inject
    lateinit var viewModel: ModalityListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ModalityListViewModel::class.java)
        lifecycle.addObserver(viewModel)

        setContentView(R.layout.activity_modality_list)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, -1)
        if (eventId == -1) {
            finish()
            return
        }
        observerModalities(eventId)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    private fun observerModalities(eventId: Int) {
        viewModel.eventId = eventId
        viewModel.getState().observe(this, Observer { newState ->
            newState?.let {
                handleState(eventId, it)
            }
        })
    }

    private fun handleState(eventId: Int, state: ViewState<Map<String, List<ModalityBinding>>>?) {
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

    private fun handleSuccess(eventId: Int, modalityDates: List<String>) {
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

        fun startActivity(context: Context, eventId: Int) {
            context.startActivity(Intent(context, ModalityListActivity::class.java).apply {
                putExtra(EXTRA_EVENT_ID, eventId)
            })
        }
    }
}
