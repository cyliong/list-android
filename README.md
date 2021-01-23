# list-android
This is a simple list mobile app (to-do list, tasks, shopping list, recipes, and the like) 
showcasing the implementation of CRUD operations, written in Kotlin for the Android platform.

This project, together with [list-ios](https://github.com/cyliong/list-ios), 
present a way to develop cross-platform native mobile apps with similar patterns and libraries, including:
- MVVM pattern
- Reactive programming
- Material Components
- Realm 

In addition, BDD-style test automation for both the Android and iOS list apps is showcased at 
[cross-platform-bdd](https://github.com/cyliong/cross-platform-bdd).

*If you are looking for a more Android-specific solution without concerning
too much about reusing similar patterns and libraries across platforms,
may refer to the [list2-android](https://github.com/cyliong/list2-android) project.*

## Features
- Display a list of items (`RecyclerView`, `RealmResults`)
- Navigate to a page to add or edit items (intent, extra, `EditText`)
- Reactively enable or disable save button upon text changes (`RxBinding`, `RxJava`)
- Swipe to delete items (`ItemTouchHelper`)
- Store items in database using data model (`RealmObject`)
- Database migration (`RealmConfiguration`, `RealmMigration`)

## Dependencies
- Realm Database
- RxBinding
- RxJava
- Material Components

## Requirements
- Android Studio version 4.1.2 or higher
- Android 4.4 (API level 19) or higher
- Kotlin 1.4 or higher