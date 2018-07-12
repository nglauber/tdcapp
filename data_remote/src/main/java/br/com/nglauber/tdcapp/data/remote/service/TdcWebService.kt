package br.com.nglauber.tdcapp.data.remote.service

import br.com.nglauber.tdcapp.data.remote.model.TdcModality
import br.com.nglauber.tdcapp.data.remote.model.TdcEvent
import br.com.nglauber.tdcapp.data.remote.model.TdcSession
import br.com.nglauber.tdcapp.data.remote.model.TdcSpeaker
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
* Para mais detalhes, acesse a documentação completa no Swagger: http://editor2.swagger.io/
* Para visualizar, importe a URL https://api.globalcode.com.br/docapi/documentacao.yml para o
* Swagger (File - Import URL...) ou copie o conteúdo do link dentro do editor dele para visualizar.
*/
interface TdcWebService {
    @GET(PATH_GET_EVENTS)
    fun getEvents(): Observable<List<TdcEvent>>

    @GET(PATH_GET_MODALITIES_BY_EVENT)
    fun getModalitiesByEvent(
            @Path(PARAM_EVENT_ID) eventId: Long
    ): Observable<List<TdcModality>>

    @GET(PATH_GET_SESSIONS_BY_MODALITY)
    fun getSessionsByModality(
            @Path(PARAM_EVENT_ID) eventId: Long,
            @Path(PARAM_MODALITY_ID) modalityId: Long
    ): Observable<List<TdcSession>>

    @GET(PATH_GET_SPEAKERS_BY_SESSION)
    fun getSpeakersBySession(
            @Path(PARAM_EVENT_ID) eventId: Long,
            @Path(PARAM_MODALITY_ID) modalityId: Long,
            @Path(PARAM_SESSION_ID) sessionId: Long
    ): Observable<List<TdcSpeaker>>

    companion object {
        const val API_BASE_URL = "https://api.globalcode.com.br/v1/publico/"

        private const val PARAM_EVENT_ID = "eventId"
        private const val PARAM_MODALITY_ID = "modalityId"
        private const val PARAM_SESSION_ID = "sessionId"

        private const val PATH_GET_EVENTS =
                "eventos"
        private const val PATH_GET_MODALITIES_BY_EVENT =
                "evento/{$PARAM_EVENT_ID}/modalidades"
        private const val PATH_GET_SESSIONS_BY_MODALITY =
                "evento/{$PARAM_EVENT_ID}/modalidade/{$PARAM_MODALITY_ID}/palestras"
        private const val PATH_GET_SPEAKERS_BY_SESSION =
                "evento/{$PARAM_EVENT_ID}/modalidade/{$PARAM_MODALITY_ID}/palestra/{$PARAM_SESSION_ID}/palestrantes"
    }
}