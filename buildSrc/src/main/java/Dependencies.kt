object Versions {
    const val coroutines = "1.3.9"
    const val appcompat = "1.2.0"
    const val activityKtx = "1.1.0"
    const val constraintlayout = "2.0.1"
    const val recyclerview = "1.1.0"
    const val material = "1.2.1"
    const val lifecycle = "2.2.0"
    const val paging = "3.0.0-alpha06"
    const val retrofit = "2.9.0"
    const val okhttp = "4.9.0"
    const val hiltAndroidx = "1.0.0-alpha02"
    const val timber = "4.7.1"
    const val junit = "4.13"
    const val ktlint = "0.39.0"
}

object BuildVersions {
    const val agp = "4.0.1"
    const val kotlin = "1.4.10"
    const val hilt = "2.29.1-alpha"
    const val detekt = "1.13.1"
    const val ktlintGradle = "9.4.0"
}

object BuildPlugins {
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val kotlinKapt = "org.jetbrains.kotlin.kapt"
    const val hilt = "dagger.hilt.android.plugin"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val ktlintGradle = "org.jlleitschuh.gradle.ktlint"
}

object Libs {
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val hilt = "com.google.dagger:hilt-android:${BuildVersions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${BuildVersions.hilt}"
    const val hiltAndroidx = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltAndroidx}"
    const val hiltAndroidxCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltAndroidx}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
}
