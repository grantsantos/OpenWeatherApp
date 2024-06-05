plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.openweatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.openweatherapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //Hilt Navigation Compose (E.g. hiltViewModel)
    implementation(libs.hilt.navigation.compose)

    //ViewModel Compose
    implementation(libs.lifecycle.viewmodel.compose)

    //Retrofit 2
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)

    //Room
    implementation(libs.room)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    //GoogleLocation Services
    implementation(libs.play.services.location)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth.ktx)

    //Coil Compose
    implementation(libs.coil.compose)

    implementation(libs.sql.cipher)
    implementation(libs.sql.lite)

    implementation(libs.security.crypto)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.google.truth)

}