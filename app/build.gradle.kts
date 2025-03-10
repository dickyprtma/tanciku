plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id ("com.google.dagger.hilt.android")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.neonusa.tanciku"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.neonusa.tanciku"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "MAIN_BANNER", "${project.findProperty("MAIN_BANNER")}")

        // setelah menambahkan kode diatas
        // build -> clean
        // build -> rebuild
        buildFeatures {
            buildConfig = true
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

    // Hilt Navcompose
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Dark theme
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.31.4-beta")

    // Room
    implementation ("androidx.room:room-runtime:2.5.2")
    ksp ("androidx.room:room-compiler:2.5.2") // don't use kapt (kapt will error)
    implementation ("androidx.room:room-ktx:2.5.2")

    // Dagger Hilt : Dependency Injection
    implementation ("com.google.dagger:hilt-android:2.48")
    kapt ("com.google.dagger:hilt-compiler:2.48")

    // Datastore : SharedPref versi jetpack
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    // splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // date format converter
    implementation ("com.jakewharton.threetenabp:threetenabp:1.4.7")

    // admob
    implementation("com.google.android.gms:play-services-ads:23.3.0")

    // paging
    implementation ("androidx.room:room-paging:2.5.0")
    implementation ("androidx.paging:paging-runtime:3.1.1")
    implementation ("androidx.paging:paging-compose:3.2.0-rc01")
}