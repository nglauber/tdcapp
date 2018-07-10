# TDC App
Sample app which access the TDC ([The Developer's Conference](http://www.thedevelopersconference.com.br/tdc/2018/saopaulo/trilhas)) REST API.

In this sample I tried to follow the [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) principles
and use some cool Android libraries.

## Screenshots
### Events List
This screen lists all events organized by Global Code organized by date and showing the most recent on top.

![EventList](./images/events_list.png)

### Modalities List
The activities (e.g.: sessions, workshops, etc.) that will/were occur in the event are displayed in this screen grouped by date.

![ModalitiesList](./images/modalities_list.png)

### Session List
This screen display the sessions for a given modality sorted by time.

![Sessionsist](./images/sessions_list.png)

### Session details
Session details are shown in this screen, including the speaker and his mini-bio.

![SessionDetails](./images/session_details.png)

## Libraries
This project is written in Kotlin and it's using the following libraries:
* AppCompat
* Android Architecture Components (View Model, Lifecycle and LiveData)
* ConstraintLayout
* Material Design Components Library
* [Glide](https://github.com/bumptech/glide)
* [Gson](https://github.com/google/gson)
* JUnit
* [Koin](https://github.com/InsertKoinIO/koin/)
* [Mockito](http://site.mockito.org/)
* [OkHttp](http://square.github.io/okhttp/)
* [Retrofit](http://square.github.io/retrofit/)
* [RXJava](https://github.com/ReactiveX/RxJava)

## Get started
This application were created using Android 3.3 Canary 2.
To run this application, you must have a *Client ID* and *Secret* to access the TDC REST API.
To get those information, access the [Global Code](https://www.globalcode.com.br/) web site, create an account and request your credentials.
After that, create the `apikey.properties` file in the root's project directory and put your credentials as below:

```
apiClientId="<YOUR_CLIENT_ID>"
apiSecret="<YOUR_API_SECRET>"
```

But if want to test the app without real data (or you cannot have an API key), just make the following change in the [PersistenceModule.kt](./app/src/main/java/br/com/nglauber/tdcapp/di/PersistenceModule.kt) file.
```kotlin
package br.com.nglauber.tdcapp.di
// ...
val persistenceModule = module {
    ...
    single {
        // Instead of this --> TdcRemoteRepository(tdcWebService = get()) as TdcRepository
        InMemoryRepository() as TdcRepository // Use this
    }
}
```
And that's it! You're good to go.


## IMPORTANT! Credits!
This sample is based on the sample presented by [Joe Birch](https://joebirch.co/) in [his course](https://caster.io/courses/android-clean-architecture) at [caster.io](https://caster.io/).

Any feedback and/or PR's are appreciated! :)