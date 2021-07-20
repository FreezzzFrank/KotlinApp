## Pattern

- MVVM

  - ![MVVM architecture](https://s3.ap-south-1.amazonaws.com/mindorks-server-uploads/mvvm.png)

  - **Model:** It represents the data and the business logic of the Android Application. It consists of the business logic - local and remote data source, model classes, repository.
  - **View:** It consists of the UI Code(Activity, Fragment), XML. It sends the user action to the ViewModel but does **not** get the response back directly. To get the response, it has to subscribe to the observables which ViewModel exposes to it.
  - **ViewModel:** It is a bridge between the View and Model(business logic). It does not have any clue which View has to use it as it does not have a direct reference to the View. So basically, the ViewModel should not be aware of the view who is interacting with. It interacts with the Model and exposes the observable that can be observed by the View.

- Languages
  - Kotlin - 90%
  - C++    - 1%
  - Java   - 9%

## Libraries Used

- Foundation

  - Components for core system capabilities, Kotlin extensions and support for multidex and automated testing.

  - [AppCompat](https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat) - Degrade gracefully on older versions of Android.
  - [Android KTX](https://developer.android.com/kotlin/ktx) - Write more concise, idiomatic Kotlin code.
  - [Test](https://developer.android.com/training/testing/) - An Android testing framework for unit and runtime UI tests.

- Architecture

  - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.

  - [Data Binding](https://developer.android.com/topic/libraries/data-binding/) - Declaratively bind observable data to UI elements.
  - [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle) - Create a UI that automatically responds to lifecycle events.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Build data objects that notify views when the underlying database changes.
  - [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - Handle everything needed for in-app navigation.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - Access your app's SQLite database with in-app objects and compile-time checks.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.
  - [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) - Manage your Android background jobs.

- UI

  - Details on why and how to use UI Components in your apps - together or separate

  - [Animations & Transitions](https://developer.android.com/training/animation/) - Move widgets and transition between screens.
  - [Fragment](https://developer.android.com/guide/components/fragments) - A basic unit of composable UI.
  - [Layout](https://developer.android.com/guide/topics/ui/declaring-layout) - Lay out widgets using different algorithms.
  - [MDC](https://www.material.io/develop/android/components) - Material Design UI Component

- Third party and miscellaneous libraries

  - [Coil](https://github.com/coil-kt/coil) for image loading
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android): for [dependency injection](https://developer.android.com/training/dependency-injection)
  - [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) for managing background threads with simplified code and reducing needs for callbacks
  - [Flow](https://kotlinlang.org/docs/flow.html) - familiar with Reactive Streams or reactive frameworks such as RxJava and project Reactor

