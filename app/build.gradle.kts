plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.hilt)
}

android {
    compileSdkVersion(Sdk.compileVersion)

    defaultConfig {
        minSdkVersion(Sdk.minVersion)
        targetSdkVersion(Sdk.targetVersion)

        applicationId = App.id
        versionCode = App.versionCode
        versionName = App.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=kotlinx.coroutines.FlowPreview"
        )
    }
}

dependencies {
    // Coroutines
    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesAndroid)
    // UI and Appcompat
    implementation(Libs.appcompat)
    implementation(Libs.activityKtx)
    implementation(Libs.constraintlayout)
    implementation(Libs.recyclerview)
    // ViewModel and LiveData
    implementation(Libs.viewmodelKtx)
    implementation(Libs.livedataKtx)
    // Retrofit
    implementation(Libs.retrofit)
    implementation(Libs.retrofitConverterGson)
    implementation(Libs.loggingInterceptor)
    // Hilt
    implementation(Libs.hilt)
    implementation(Libs.hiltAndroidx)
    kapt(Libs.hiltCompiler)
    kapt(Libs.hiltAndroidxCompiler)
    // Timber
    implementation(Libs.timber)

    // Unit Tests
    testImplementation(TestLibs.junit)
}
