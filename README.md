# 🍽️ Food Planner Application

## 🌟 Overview

Food Planner is an **Android** app built with **Java** and follows the **MVP Architecture**. It helps users **plan meals**, **discover recipes**, and **save favorites** while offering **offline access** and **smooth animations**. Firebase is integrated for **authentication** and **data backup**.

## 🚀 Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/Nouragami7/Food-planner-application.git
   ```
2. Open the project in **Android Studio**.
3. Ensure you have the necessary dependencies installed.
4. Build and run the application on an emulator or physical device.

## 📌 Prerequisites

- **Android Studio** (latest version recommended)
- **Java 8+**
- **Google Firebase account** (for authentication and backup services)
- **Internet connection** (for fetching recipes and meal data)

## 🔗 Dependencies

### 🔥 **Firebase Services** (Authentication & Database)
- Used for **Google Sign-In** & **cloud backup**
```gradle
implementation platform('com.google.firebase:firebase-bom:33.8.0')
implementation 'com.google.firebase:firebase-auth'
implementation 'com.google.firebase:firebase-database'
```

### 🌍 **Networking & API Communication** (Retrofit)
- Fetches meal data from APIs
```gradle
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```

### 💾 **Local Storage** (Room Database & Shared Preferences)
- Saves **favorites** and **weekly meal plans** for **offline access**
```gradle
implementation 'androidx.room:room-runtime:2.6.1'
annotationProcessor 'androidx.room:room-compiler:2.6.1'
implementation 'androidx.room:room-rxjava3:2.6.1'
```

### 🔑 **Authentication & User Management**
- Google Sign-In for authentication
```gradle
implementation 'com.google.android.gms:play-services-auth:21.0.0'
```

### ⚡ **Reactive Programming** (RxJava)
- Handles **asynchronous operations**
```gradle
implementation 'io.reactivex.rxjava3:rxandroid:3.0.2'
implementation 'io.reactivex.rxjava3:rxjava:3.1.5'
implementation 'com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0'
```

### 🎨 **UI & Animation Enhancements**
- **Material Design** Components
- **Glide** for image loading
- **Lottie** for animations
```gradle
implementation 'com.google.android.material:material:1.11.0'
implementation 'com.github.bumptech.glide:glide:4.16.0'
implementation 'com.airbnb.android:lottie:5.2.0'
```

## 🌟 Features

### 🔐 **User Authentication & Data Management**
- **Google Sign-In** & **Firebase Authentication**
- **Secure Login, Logout & Session Management**
- **Data Synchronization with Firebase** (Favorites & Meal Plans)

### 🍲 **Meal Discovery & Inspiration**
- View a **Daily Inspiration Meal** 🍛
- Search meals by **Category, Ingredient, or Country**
- Explore **Popular Meals** worldwide 🌎

### ❤️ **Favorites & Saved Meals**
- **Save Meals to Favorites** ⭐
- **Delete Meals from Favorites** with a smooth **delete animation**
- Offline access to **saved meals** via **Room Database**

### 📆 **Meal Planning & Scheduling**
- Plan and schedule **weekly meals** 🗓️
- Access planned meals **offline**

### 🎥 **Cooking Assistance & Tutorials**
- Watch **Embedded Cooking Tutorial Videos** 📹
- View **Ingredients & Cooking Steps** 📝

### 📶 **Offline Mode & Performance**
- View **Saved Meals & Weekly Meal Plans** **without the internet** 🚀
- Smooth **Lottie Animations** for a better user experience ✨

## 🛠️ How to Use

### ✅ **Sign Up or Log In**
- **New users** can sign up with **Google Sign-In**
- **Returning users** can log in to access **saved meals & meal plans**

### 🔍 **Browse Meals**
- View **Meal of the Day** 🍽️ for inspiration
- Search meals by **Category, Ingredient, or Country**
- Explore **Popular Meals** from various countries 🌍

### ❤️ **Save & Manage Favorites**
- Tap the **Add to favourite button** ❤️ to **add a meal to favorites**
- Saved meals are **stored locally** (offline access)
- **Delete meals** from favorites by clicking at **Delete button** ✨

### 📆 **Plan Your Meals**
- Schedule meals for the week 🗓️
- View planned meals **offline**

### 🔄 **Sync Data with Firebase**
- Upon login, **Favorites & Meal Plans** are **restored** from Firebase

### 🎥 **Watch Cooking Videos**
- Each meal has an **embedded tutorial video** 📺

### 📶 **Offline Mode**
- View **Saved Meals & Meal Plans** **without an internet connection**

## 🛠️ Technologies Used

- **Java** ☕ for Android development
- **MVP Architecture** 🏛️ for code organization
- **Retrofit** 🌍 for API calls
- **Room Database** 💾 for local storage
- **Firebase Authentication** 🔥 for login & user management
- **RxJava** ⚡ for smooth UI & background tasks
- **Glide** 🖼️ for efficient image loading
- **Lottie** 🎨 for UI animations

## 📜 Dependency Management

To update dependencies, run:
```sh
./gradlew build
```
Or manually update them in `build.gradle (Module: app)`.

## 🤝 Contributing

Feel free to submit a **pull request** or report **issues**!

---

