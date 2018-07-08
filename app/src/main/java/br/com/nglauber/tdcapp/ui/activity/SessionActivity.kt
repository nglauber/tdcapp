package br.com.nglauber.tdcapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.presentation.AppViewModelFactory
import br.com.nglauber.tdcapp.presentation.SessionViewModel
import br.com.nglauber.tdcapp.presentation.ViewState
import br.com.nglauber.tdcapp.presentation.model.SessionBinding
import br.com.nglauber.tdcapp.presentation.model.SpeakerBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_session_content.*
import kotlinx.android.synthetic.main.item_speaker.view.*
import javax.inject.Inject


class SessionActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    @Inject
    lateinit var viewModel: SessionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SessionViewModel::class.java)

        setContentView(R.layout.activity_session)

        val session = intent.getParcelableExtra<SessionBinding>(EXTRA_SESSION)
        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, -1)
        val modalityId = intent.getIntExtra(EXTRA_MODALITY_ID, -1)
        if (eventId == -1 || modalityId == -1 || session == null) {
            finish()
            return
        }
        fetchSpeakers(eventId, modalityId, session)
    }

    private fun fetchSpeakers(eventId: Int, modalityId: Int, session: SessionBinding) {
        viewModel.getState().observe(this, Observer { newState ->
            newState?.let {
                handleState(it)
            }
        })
        if (viewModel.getState().value == null) {
            viewModel.fetchSpeakersBySession(eventId, modalityId, session)
        }
    }

    private fun handleState(state: ViewState<Pair<SessionBinding, List<SpeakerBinding>>>) {
        when (state.status) {
            ViewState.Status.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            ViewState.Status.SUCCESS -> {
                state.data?.let {
                    val (session, speakers) = it
                    handleSuccess(session, speakers)
                }
            }
            ViewState.Status.ERROR -> {
                state.error?.let {
                    handleError(it)
                }
            }
        }
    }

    private fun handleSuccess(session: SessionBinding, speakerList: List<SpeakerBinding>?) {
        progressBar.visibility = View.GONE
        txtSpeakersTitle.visibility = View.VISIBLE

        txtTitle.text = session.title
        txtTime.text = session.time
        txtDescription.text = fromHtml(session.description)

        speakerList?.forEach {
            val view = LayoutInflater.from(this)
                    .inflate(R.layout.item_speaker, containerSpeakers, false)

            if (it.miniBio.urlPhoto?.isBlank() == false) {
                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.drawable.ic_person)
                Glide.with(this)
                        .setDefaultRequestOptions(requestOptions)
                        .load(it.miniBio.urlPhoto)
                        .into(view.imgPhoto)
            }
            view.txtName.text = "${it.member.name} (${it.member.company})"
            view.txtMiniBio.text = fromHtml(it.miniBio.text ?: "")
            view.txtSocial.text = listOf(
                    it.miniBio.urlSite,
                    it.miniBio.urlBlog,
                    it.miniBio.urlTwitter,
                    it.miniBio.urlLinkedin)
                    .filter { it != null && it.isNotEmpty() }
                    .joinToString("\n")
            containerSpeakers.addView(view)
        }
    }

    private fun handleError(e: Throwable) {
        e.printStackTrace()
        progressBar.visibility = View.GONE
        Toast.makeText(this, R.string.error_loading_speakers, Toast.LENGTH_SHORT).show()

    }

    private fun fromHtml(string: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(string, 0)
        } else {
            Html.fromHtml(string)
        }
    }

    companion object {
        private const val EXTRA_SESSION = "session"
        private const val EXTRA_MODALITY_ID = "modalityId"
        private const val EXTRA_EVENT_ID = "eventId"

        fun startActivity(context: Context, eventId: Int, modalityId: Int, session: SessionBinding) {
            context.startActivity(Intent(context, SessionActivity::class.java).apply {
                putExtra(EXTRA_EVENT_ID, eventId)
                putExtra(EXTRA_MODALITY_ID, modalityId)
                putExtra(EXTRA_SESSION, session)
            })
        }
    }
}
