plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.org.jetbrains.kotlin.android)

    //dagger.hilt
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.locaspes.feed"
    compileSdk = 35

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.firebase.firestore.ktx)

//    // Unit-тесты
//    testImplementation(libs.junit)
//    testImplementation(libs.mockito.core)
//    testImplementation(libs.mockito.kotlin)
//    testImplementation(libs.mockk)
//    testImplementation(libs.kotlinx.coroutines.test)
//    testImplementation(libs.truth)
//    testImplementation(libs.androidx.core.testing)

//    // Android-тесты
//    androidTestImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.mockito.core)
//    androidTestImplementation(libs.mockito.kotlin)
//    androidTestImplementation("io.mockk:mockk-android:1.13.13")
//    androidTestImplementation(libs.kotlinx.coroutines.test)
//    androidTestImplementation(libs.truth)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.test.manifest)



    implementation(project(":data"))
    implementation(project(":theme"))
    implementation(project(":core"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)
    debugImplementation(libs.androidx.ui.test.manifest)


    //dagger.hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

}

kapt {
    correctErrorTypes = true
}


