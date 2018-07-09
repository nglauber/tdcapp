package br.com.nglauber.tdcapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.nglauber.tdcapp.domain.interactor.session.GetSpeakersBySession
import br.com.nglauber.tdcapp.domain.model.Speaker
import br.com.nglauber.tdcapp.presentation.mapper.MemberMapper
import br.com.nglauber.tdcapp.presentation.mapper.MiniBioMapper
import br.com.nglauber.tdcapp.presentation.mapper.SpeakerMapper
import br.com.nglauber.tdcapp.test.DomainDataFactory
import br.com.nglauber.tdcapp.test.PresentationDataFactory
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class SessionViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mapper = SpeakerMapper(MemberMapper(), MiniBioMapper()) // TODO improve this
    private var getSpeakersBySession = mock<GetSpeakersBySession>()
    private var sessionViewModel = SessionViewModel(getSpeakersBySession, mapper)

    @Captor
    private val captor = argumentCaptor<((List<Speaker>) -> Unit)>()

    @Captor
    private val captorError = argumentCaptor<((Throwable) -> Unit)>()

    @Test
    fun fetchSpeakersExecutesUseCase() {
        sessionViewModel.fetchSpeakersBySession(1, 2, PresentationDataFactory.makeSession())
        verify(getSpeakersBySession, times(1))
                .execute(any(), any(), any(), eq(null))
    }

    @Test
    fun fetchSpeakersReturnsSuccess() {
        val eventId = 1
        val modality = 2
        val sessionBinding = PresentationDataFactory.makeSession()
        val speakers = DomainDataFactory.makeSpeakersList(2)

        sessionViewModel.fetchSpeakersBySession(eventId, modality, sessionBinding)

        verify(getSpeakersBySession).execute(any(), captor.capture(), any(), eq(null))
        captor.firstValue.invoke(speakers)

        assertEquals(
                ViewState.Status.SUCCESS,
                sessionViewModel.getState().value?.status)
    }

    @Test
    fun fetchSpeakersReturnsData() {
        val eventId = 1
        val modality = 2
        val sessionBinding = PresentationDataFactory.makeSession()

        val speakers = DomainDataFactory.makeSpeakersList(2)
        val speakerBindings = speakers.map { mapper.parse(it) }

        sessionViewModel.fetchSpeakersBySession(eventId, modality, sessionBinding)
        verify(getSpeakersBySession).execute(any(), captor.capture(), any(), eq(null))
        captor.firstValue.invoke(speakers)

        val map = sessionBinding to speakerBindings
        assertEquals(
                map,
                sessionViewModel.getState().value?.data
        )
    }

    @Test
    fun fetchSpeakersReturnsError() {
        val eventId = 1
        val modality = 2
        val sessionBinding = PresentationDataFactory.makeSession()

        //TODO this test is weird...
        sessionViewModel.fetchSpeakersBySession(eventId, modality, sessionBinding)
        verify(getSpeakersBySession).execute(any(), captor.capture(), captorError.capture(), eq(null))
        captorError.firstValue.invoke(RuntimeException())

        Assert.assertEquals(
                ViewState.Status.ERROR,
                sessionViewModel.getState().value?.status)
    }
}