# list-android
This is a simple list mobile app (to-do list, tasks, shopping list, recipes, and the like) showcasing the implementation of CRUD operations, MVVM pattern and reactive programming, written in Kotlin for the Android platform.

Together with [list-ios](https://github.com/cyliong/list-ios), they present a way to develop cross-platform native mobile apps with similar patterns and libraries. In addition, BDD-style test automation for both the Android and iOS list apps is showcased at [cross-platform-bdd](https://github.com/cyliong/cross-platform-bdd).

## Features
- Display a list of items (`RecyclerView`, `RealmResults`)
- Navigate to a page to add or edit items (intent, extra, `EditText`)
- Reactively enable or disable save button upon text changes (`RxBinding`, `RxJava`)
- Swipe to delete items (`ItemTouchHelper`)
- Store items in database using data model (`RealmObject`)

## Dependencies
- Kotlin Android Extensions
- kapt
- Realm Database
- RxBinding
- RxJava

## Requirements
- Android Studio version 3.6 or higher
- Android 4.4 (API level 19) or higher
- Kotlin 1.3 or higher