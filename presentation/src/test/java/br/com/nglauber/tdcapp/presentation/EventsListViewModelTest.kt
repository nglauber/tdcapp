package br.com.nglauber.tdcapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.nglauber.tdcapp.domain.interactor.event.GetEvents
import br.com.nglauber.tdcapp.domain.model.Event
import br.com.nglauber.tdcapp.presentation.mapper.EventMapper
import br.com.nglauber.tdcapp.presentation.test.DomainDataFactory
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class EventsListViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var getEvents = mock<GetEvents>()
    private var eventListViewModel = EventsListViewModel(getEvents, EventMapper())

    @Captor
    private val captor = argumentCaptor<((List<Event>) -> Unit)>()

    @Captor
    private val captorError = argumentCaptor<((Throwable) -> Unit)>()

    @Test
    fun fetchEventsExecutesUseCase() {
        eventListViewModel.fetchEvents()
        verify(getEvents, times(1))
                .execute(eq(null), any(), any(), eq(null))
    }

    @Test
    fun fetchEventsReturnsSuccess() {
        val events = DomainDataFactory.makeEventsList(2)

        eventListViewModel.fetchEvents()

        verify(getEvents).execute(eq(null), captor.capture(), any(), eq(null))
        captor.firstValue.invoke(events)

        assertEquals(
                ViewState.Status.SUCCESS,
                eventListViewModel.getState().value?.status)
    }

    @Test
    fun fetchEventsReturnsData() {
        val mapper = EventMapper()
        val events = DomainDataFactory.makeEventsList(2)
        val eventBindings = events.map { mapper.fromDomain(it) }

        eventListViewModel.fetchEvents()
        verify(getEvents).execute(eq(null), captor.capture(), any(), eq(null))
        captor.firstValue.invoke(events)

        assertEquals(
                eventBindings,
                eventListViewModel.getState().value?.data
        )
    }

    @Test
    fun fetchProjectsReturnsError() {
        eventListViewModel.fetchEvents()
        verify(getEvents).execute(eq(null), any(), captorError.capture(), eq(null))
        captorError.firstValue.invoke(RuntimeException())

        assertEquals(
                ViewState.Status.ERROR,
                eventListViewModel.getState().value?.status)
    }
}