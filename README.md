# iTunes Tracks

## Libraries

- Kotlin
- AndroidX
- Material Components
- Android Architecture Components
    - Lifecycle and ViewModel
    - Navigation
        - SafeArgs
- Retrofit
- Kotlinx Serialization
- Kotlin Coroutines
- Coil
- Dagger Hilt

## Clean Architecture

This app demonstrates the use of clean architecture which consists of the following layers:

*presentation* - responsible for having Activities or Fragments with its corresponding ViewModels and Adapter

*domain* - responsible for having the entities and use cases for the presentation layer to connect with the data layer

*data* - responsible for having data models and repositories for getting data

*di* - responsible for dependency injection. In this project we used Dagger Hilt

## Authors

- Jayzon Jorge F. Alcancia - [jayzonjorgealcancia@gmail.com](mailto:jayzonjorgealcancia@gmail.com)
