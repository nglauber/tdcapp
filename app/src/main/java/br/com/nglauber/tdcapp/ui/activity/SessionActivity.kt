package br.com.nglauber.tdcapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.widget.Toast
import br.com.nglauber.tdcapp.R
import br.com.nglauber.tdcapp.repository.remote.model.TdcSession
import br.com.nglauber.tdcapp.repository.remote.model.TdcSpeaker
import br.com.nglauber.tdcapp.repository.remote.service.TdcWebServiceFactory
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_session_content.*
import kotlinx.android.synthetic.main.item_speaker.view.*
import com.bumptech.glide.request.RequestOptions



class SessionActivity : AppCompatActivity() {

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)

        val session = intent.getParcelableExtra<TdcSession>(EXTRA_SESSION)
        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, -1)
        val modalityId = intent.getIntExtra(EXTRA_MODALITY_ID, -1)
        if (eventId == -1 || modalityId == -1 || session == null) {
            finish()
            return
        }

        txtTitle.text = session.title
        txtTime.text = session.time
        txtDescription.text = fromHtml(session.description)

        fetchSpeakers(eventId, modalityId, session.id)
    }

    private fun fetchSpeakers(eventId: Int, activityId: Int, sessionId: Int) {
        val service = TdcWebServiceFactory().makeTdWebService(this, true)
        disposable = service.getSpeakersBySession(eventId, activityId, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { speakerList ->
                            handleSuccess(speakerList)
                        },
                        { e ->
                            handleError(e)
                        }
                )
    }

    private fun handleSuccess(speakerList: List<TdcSpeaker>?) {
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
        Toast.makeText(this, R.string.error_loading_speakers, Toast.LENGTH_SHORT).show()

    }

    private fun fromHtml(string: String): Spanned{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(string, 0)
        } else {
            Html.fromHtml(string)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    companion object {
        private const val EXTRA_SESSION = "session"
        private const val EXTRA_MODALITY_ID = "modalityId"
        private const val EXTRA_EVENT_ID = "eventId"

        fun startActivity(context: Context, session: TdcSession, activityId: Int, eventId: Int) {
            context.startActivity(Intent(context, SessionActivity::class.java).apply {
                putExtra(EXTRA_EVENT_ID, eventId)
                putExtra(EXTRA_MODALITY_ID, activityId)
                putExtra(EXTRA_SESSION, session)
            })
        }
    }
}
