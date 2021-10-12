# list-android
This is a simple list mobile app 
(to-do list, tasks, shopping list, recipes, and the like) 
showcasing the implementation of CRUD operations, 
written in Kotlin for the Android platform.

This project, 
together with [list-ios](https://github.com/cyliong/list-ios), 
presents a way to develop cross-platform native mobile apps 
with similar patterns and libraries, including:
- MVVM pattern
- Reactive programming
- Material Components
- Realm
- Flutter module

In addition, BDD-style test automation 
for both the Android and iOS list apps is showcased at 
[cross-platform-bdd](https://github.com/cyliong/cross-platform-bdd).

*A Flutter module, [list-module](https://github.com/cyliong/list-module)
is to be integrated into this project to share code written in Dart
across platforms.*

*However, if you are looking for a pure native solution
without the need to have a single codebase across platforms, refer to 
the [native](https://github.com/cyliong/list-android/tree/native) branch.*

*Alternatively, if you are looking for a more Android-specific solution 
without concerning too much about reusing similar patterns 
and libraries across platforms, refer to 
the [list2-android](https://github.com/cyliong/list2-android) project.*

## Features
- Display a list of items (`RecyclerView`, `RealmResults`)
- Navigate to a page to add or edit items (intent, extra, `EditText`)
- Reactively enable or disable save button upon text changes 
  (`RxBinding`, `RxJava`)
- Swipe to delete items (`ItemTouchHelper`)
- Store items in database using data model (`RealmObject`)
- Database migration (`RealmConfiguration`, `RealmMigration`)
- About page (Flutter module)

## Dependencies
- Realm Database
- RxBinding
- RxJava
- Material Components
- Flutter

## Requirements
- Android Studio Arctic Fox | 2020.3.1 Patch 3 or newer
- Android 4.4 (API level 19) or higher
- Kotlin 1.5 or higher

## Setup
1. Download this project (list-android) and 
   [list-module](https://github.com/cyliong/list-module) 
   then place them in the same directory (e.g. `path/to/`):
```
path/to/
├── list-android/
└── list-module/
```
2. Run the following to set up list-module:
```
cd path/to/list-module
```
```
flutter pub get
```
3. Open list-android in Android Studio and run the app.