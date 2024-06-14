import com.android.tools.r8.internal.im

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "hao.com.appbanhang"
    compileSdk = 34

    defaultConfig {
        applicationId = "hao.com.appbanhang"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            abiFilters.add("x86")
            abiFilters.add("armeabi")
            abiFilters.add("armeabi-v7a")
            //abiFilters ABI_FILTERS
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
        jvmTarget = "21"
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core:1.13.0")
    implementation("com.android.support:support-compat:28.0.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.9.0")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation(files("D:\\Downloads\\Compressed\\zalopay\\zpdk-release-v3.1.aar"))
    implementation("androidx.core:core-ktx:+")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //RxJava
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("io.reactivex.rxjava3:rxjava:3.1.8")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
    //bradge
    implementation("com.nex3z:notification-badge:1.0.5")
    //event bus
    implementation("org.greenrobot:eventbus:3.3.1")
    //paper
    implementation("io.github.pilgr:paperdb:2.7.2")
    //Gson
    implementation("com.google.code.gson:gson:2.10.1")
    //lotte
    implementation("com.airbnb.android:lottie:6.4.0")
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-messaging:23.4.1")
    implementation("com.google.firebase:firebase-analytics:21.6.2")
    implementation("com.google.firebase:firebase-firestore:24.11.1")
    //momo
    implementation("com.github.momo-wallet:mobile-sdk:1.0.7")
    //zalo
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("commons-codec:commons-codec:1.17.0")
    //youtube
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
    //sliderimage
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    //livestream
    implementation("live.videosdk:rtc-android-sdk:0.1.27")
    implementation("com.amitshekhar.android:android-networking:1.0.2")
    implementation("com.google.android.exoplayer:exoplayer:2.19.1")
    //paypal
    implementation("com.paypal.checkout:android-sdk:1.3.2")
}