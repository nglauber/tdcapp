package br.com.nglauber.tdcapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.nglauber.tdcapp.domain.interactor.modality.GetModalitiesByEvent
import br.com.nglauber.tdcapp.domain.model.Modality
import br.com.nglauber.tdcapp.presentation.mapper.ModalityMapper
import br.com.nglauber.tdcapp.presentation.test.DomainDataFactory
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class ModalityListViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var getModalitiesByEvent = mock<GetModalitiesByEvent>()
    private var modalityListViewModel = ModalityListViewModel(getModalitiesByEvent, ModalityMapper())

    @Captor
    private val captor = argumentCaptor<((List<Modality>) -> Unit)>()

    @Captor
    private val captorError = argumentCaptor<((Throwable) -> Unit)>()

    @Test
    fun fetchModalityExecutesUseCase() {
        modalityListViewModel.fetchModalities(1)
        verify(getModalitiesByEvent, times(1))
                .execute(any(), any(), any(), eq(null))
    }

    @Test
    fun fetchModalitiesReturnsSuccess() {
        val eventId = 1L
        val modalities = DomainDataFactory.makeModalitiesList(2)

        modalityListViewModel.fetchModalities(eventId)

        verify(getModalitiesByEvent).execute(any(), captor.capture(), any(), eq(null))
        captor.firstValue.invoke(modalities)

        assertEquals(
                ViewState.Status.SUCCESS,
                modalityListViewModel.getState().value?.status)
    }

    @Test
    fun fetchModalitiesReturnsData() {
        val mapper = ModalityMapper()
        val modalities = DomainDataFactory.makeModalitiesList(2)
        val modalityBindings = modalities.map { mapper.fromDomain(it) }

        modalityListViewModel.fetchModalities(1)
        verify(getModalitiesByEvent).execute(any(), captor.capture(), any(), eq(null))
        captor.firstValue.invoke(modalities)

        val bindingsMap = modalities
                .map { mapper.fromDomain(it) }
                .sortedWith(compareBy({ it.date }, { it.positionOnEvent }))
                .groupBy { it.date }

        assertEquals(
                bindingsMap,
                modalityListViewModel.getState().value?.data
        )
    }

    @Test
    fun fetchModalitiesReturnsError() {
        //TODO this test is weird...
        modalityListViewModel.fetchModalities(1)
        verify(getModalitiesByEvent).execute(any(), captor.capture(), captorError.capture(), eq(null))
        captorError.firstValue.invoke(RuntimeException())

        assertEquals(
                ViewState.Status.ERROR,
                modalityListViewModel.getState().value?.status)
    }
}