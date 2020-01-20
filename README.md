# Movies Paginated list using kotlin and MVVM architechture

[![Kotlin](https://kotlin.link/awesome-kotlin.svg)](https://kotlinlang.org/)
[![Kotlin Version](https://img.shields.io/badge/kotlin-1.3.61-blue.svg)](http://kotlinlang.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A movies sample android project in Kotlin which uses clean architecture

### Clean Architecture Description and Implementation

This project uses MVVM with RxJava, androidx and android lifecycle components ViewModel, LiveData,Room and paging library to display a list of movies from [The Movie Database](https://www.themoviedb.org) using Retrofit . Selecting a movie will display a movie detail page including title, overview, image,  and the genres it belongs to.

After bring the movies from the Api , the results will be saved in local DB using Room , the repository will achieve the Single source of truth with the help of RxJava to combine multible resources , which will send the data to the Viewmodel where will be data Transformation and get the final result in Livedata objects and send it to the View.

The Idea behind using local DB to achieve 3 things:

1 - to make the user expirience better when the user open the app by showing few results and refresh the list from the API.

2 - make it easier to transfer the data between the fragments and activities without the need of creating Parcelable objects. 

3 - Save some results from the Api like in our example (Genres, Languages) to show it to the user when needed 

### Package Details
**App:** Contain the Apiservices , RoomDB and Application class

* **DI:** Contain the DI modules 
* **MoviesList:** contain the files related to the getting the data of the movies list and saving it in local DB and show it in the list
* **Genres and Languages :** to get the genres and languages for 1 time from API and save it in local to use it when it's needed
* **Utils:** contain helpers for doing the pagination (Boundary callback , and paging helper)
and other utils like add fragment or image utils to help with a clean architecture 
 

### Frameworks and Libraries

* Architecture components with ViewModel and LiveData ,Paging , Room
* RxJava for async processing
* Gson for Json Parsing
* Koin for dependency injection
* AndroidX for Jetpack components
* Glide for Image Loading

