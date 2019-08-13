# list-android
This is a simple list mobile app (to-do list, tasks, shopping list, recipes, and the like) showcasing the implementation of CRUD operations, written in Kotlin for the Android platform.

Together with [list-ios](https://github.com/cyliong/list-ios), they present a way to develop cross-platform native mobile apps with similar patterns and libraries.

## Features
- Display a list of items (`RecyclerView`, `RealmResults`)
- Input dialog for adding and editing items (Anko Commons and Anko Layouts)
- Swipe to delete items (`ItemTouchHelper`)
- Store items in database using data model (`RealmObject`)

## Dependencies
- Kotlin Android Extensions
- kapt
- Anko
- Realm Database

## Requirements
- Android Studio version 3.4 or higher
- Android 4.4 (API level 19) or higher
- Kotlin 1.3 or higher