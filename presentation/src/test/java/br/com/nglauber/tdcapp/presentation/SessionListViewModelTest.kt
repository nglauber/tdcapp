package br.com.nglauber.tdcapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.nglauber.tdcapp.domain.interactor.session.GetSessionsByModality
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.presentation.mapper.SessionMapper
import br.com.nglauber.tdcapp.presentation.test.DomainDataFactory
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class SessionListViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var getSessionsByModality = mock<GetSessionsByModality>()
    private var sessionListViewModel = SessionListViewModel(getSessionsByModality, SessionMapper())

    @Captor
    private val captor = argumentCaptor<((List<Session>) -> Unit)>()

    @Captor
    private val captorError = argumentCaptor<((Throwable) -> Unit)>()

    @Test
    fun fetchSessionsExecutesUseCase() {
        sessionListViewModel.fetchSessionsByModality(1, 2)
        verify(getSessionsByModality, times(1))
                .execute(any(), any(), any(), eq(null))
    }

    @Test
    fun fetchSessionsReturnsSuccess() {
        val eventId = 1
        val modalityId = 2
        val sessions = DomainDataFactory.makeSessionsList(2)

        sessionListViewModel.fetchSessionsByModality(eventId, modalityId)

        verify(getSessionsByModality).execute(any(), captor.capture(), any(), eq(null))
        captor.firstValue.invoke(sessions)

        assertEquals(eventId, sessionListViewModel.eventId)
        assertEquals(modalityId, sessionListViewModel.modalityId)

        assertEquals(
                ViewState.Status.SUCCESS,
                sessionListViewModel.getState().value?.status)
    }

    @Test
    fun fetchSessionsReturnsData() {
        val mapper = SessionMapper()
        val sessions = DomainDataFactory.makeSessionsList(2)
        val sessionBindings = sessions.map { mapper.parse(it) }

        sessionListViewModel.fetchSessionsByModality(1, 2)
        verify(getSessionsByModality).execute(any(), captor.capture(), any(), eq(null))
        captor.firstValue.invoke(sessions)

        assertEquals(
                sessionBindings,
                sessionListViewModel.getState().value?.data
        )
    }

    @Test
    fun fetchSessionsReturnsError() {
        //TODO this test is weird...
        sessionListViewModel.fetchSessionsByModality(1, 2)
        verify(getSessionsByModality).execute(any(), captor.capture(), captorError.capture(), eq(null))
        captorError.firstValue.invoke(RuntimeException())

        assertEquals(
                ViewState.Status.ERROR,
                sessionListViewModel.getState().value?.status)
    }
}