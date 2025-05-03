plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.org.jetbrains.kotlin.android)

    //dagger.hilt
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.locaspes.data"
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.espresso.core)
    //implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)


    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.runtime)

    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //cloudinary
    // Cloudinary Android SDK
    implementation("com.cloudinary:cloudinary-android:3.0.2")
    // Coil для загрузки изображений в Compose
    implementation("io.coil-kt:coil-compose:2.7.0")
    // Для выбора файлов (опционально)
    implementation("androidx.activity:activity-compose:1.9.0")

    implementation("id.zelory:compressor:3.0.1")

    //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation("com.google.code.gson:gson:2.10.1")



    implementation(project(":core"))

    //tests
    androidTestApi(libs.junit)

    debugApi(libs.androidx.ui.test.manifest)

}

kapt {
    correctErrorTypes = true
}