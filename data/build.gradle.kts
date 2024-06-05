plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs") version "2.5.3"
    id("org.jetbrains.kotlin.kapt")

}

android {
    namespace = "com.example.data"
    compileSdk = 34


    defaultConfig {
        minSdk = 24


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }
    buildTypes {

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":common"))

    //noinspection UseTomlInstead
    implementation("androidx.core:core-ktx:1.12.0")
    //noinspection UseTomlInstead
    implementation("androidx.appcompat:appcompat:1.6.1")
    //noinspection UseTomlInstead
    implementation("com.google.android.material:material:1.11.0")
    //noinspection UseTomlInstead
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Retrofit
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //noinspection UseTomlInstead
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    //di
    //noinspection UseTomlInstead
    implementation ("com.google.dagger:hilt-android:2.51.1")
    //noinspection UseTomlInstead
    kapt ("com.google.dagger:hilt-compiler:2.51.1")
    //noinspection UseTomlInstead
    kapt ("androidx.hilt:hilt-compiler:1.0.0")


    // Room and Lifecycle dependencies
    //noinspection UseTomlInstead
    implementation ("androidx.room:room-runtime:2.6.1")
    //noinspection UseTomlInstead
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    //noinspection UseTomlInstead
    implementation("androidx.test:core-ktx:1.5.0")
    //noinspection KaptUsageInsteadOfKsp,UseTomlInstead
    kapt ("androidx.room:room-compiler:2.6.1")
    //noinspection UseTomlInstead
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    //noinspection UseTomlInstead
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    // Kotlin Extensions and Coroutines support for Room
    //noinspection UseTomlInstead
    implementation ("androidx.room:room-ktx:2.6.1")

    // Navigation
    //noinspection UseTomlInstead
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    //noinspection UseTomlInstead
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // Coroutines
    //noinspection UseTomlInstead
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    //noinspection UseTomlInstead
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    // Glide
    //noinspection UseTomlInstead
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    //Datastore
    //noinspection UseTomlInstead
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    //noinspection UseTomlInstead
    implementation("androidx.datastore:datastore-preferences-core:1.1.1")

    //Koin
    //noinspection UseTomlInstead
    implementation("io.insert-koin:koin-core:3.2.0")
    //noinspection UseTomlInstead
    implementation("io.insert-koin:koin-android:3.2.0")

    //noinspection UseTomlInstead
    implementation ("com.mikepenz:fastadapter:5.6.0")


    //noinspection UseTomlInstead
    testImplementation("junit:junit:4.13.2")
    //noinspection UseTomlInstead
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    //noinspection UseTomlInstead
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}