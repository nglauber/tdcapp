/*
 * Copyright (C) 2018 Diego Figueredo do Nascimento.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.nglauber.tdcapp.di

import br.com.nglauber.tdcapp.domain.interactor.event.GetEvents
import br.com.nglauber.tdcapp.domain.interactor.modality.GetModalitiesByEvent
import br.com.nglauber.tdcapp.domain.interactor.session.GetSessionsByModality
import br.com.nglauber.tdcapp.domain.interactor.session.GetSpeakersBySession
import br.com.nglauber.tdcapp.presentation.mapper.*
import org.koin.dsl.module.module


val domainModule = module {
    factory { EventMapper() }
    single { GetEvents(get(), get()) }

    factory { ModalityMapper() }
    single { GetModalitiesByEvent(get(), get()) }

    factory { SessionMapper() }
    single { GetSessionsByModality(get(), get()) }

    factory { MemberMapper() }
    factory { MiniBioMapper() }
    factory { SpeakerMapper(get(), get()) }
    single { GetSpeakersBySession(get(), get()) }
}