plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}


android {
    namespace = "com.example.entrenet"
    compileSdk = 35

    buildFeatures {
        viewBinding = true
    }


    defaultConfig {
        applicationId = "com.example.entrenet"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Add BuildConfig field for API key (read from local.properties)
        buildConfigField("String", "GEMINI_API_KEY", "\"${project.findProperty("Bearer AIzaSyCfjjedKE5d8Jt6tZZ6ZGmz1Swk5Iug2zM") ?: ""}\"")

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
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation(libs.converter.moshi)
    implementation(libs.common)
    implementation(libs.generativeai)
    implementation(libs.firebase.vertexai)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("com.airbnb.android:lottie:6.1.0")

}
