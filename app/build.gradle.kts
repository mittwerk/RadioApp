@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.hilt)
    alias(libs.plugins.serialization)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-parcelize")
    id("com.google.relay") version "0.3.11"
}

secrets {
    propertiesFileName = "local.properties"
    ignoreList.add("keyToIgnore")
}

android {
    namespace = "space.mittwerk.radioapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "space.mittwerk.radioapp"
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
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }

        create("benchmark") {
            initWith(getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
        }
        create("benchmark1") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.runtime.tracing)

    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.constraintlayout.compose)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    implementation(libs.radiobrowser4j)

    implementation(libs.hilt.android)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.work)

    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    annotationProcessor(libs.dagger.android.processor)
    annotationProcessor(libs.dagger.compiler)

    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.work.multiprocess)

    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.placeholder.material)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.ktorfit.lib)
    ksp(libs.ktorfit.ksp)
    implementation(libs.ktor.client.okhttp)

    implementation(platform(libs.arrow.stack))
    implementation(libs.arrow.core)
    implementation(libs.arrow.core.retrofit)
    implementation(libs.arrow.core.serialization)
    implementation(libs.arrow.fx.coroutines)
    implementation(libs.arrow.optics)
    implementation(libs.arrow.annotations)
    implementation(libs.arrow.resilience)
    implementation(libs.arrow.atomic)
    implementation(libs.arrow.fx.stm)
    implementation(libs.arrow.optics.reflect)
    ksp(libs.arrow.optics.ksp.plugin)

    ksp(libs.kopykat.ksp)

    implementation(libs.ktor.client.content.negotiation)

    implementation(libs.acra.mail)
    implementation(libs.acra.dialog)

    implementation(libs.quiver)
    implementation(libs.quiver.test)

    implementation(libs.androidx.sqlite)
    implementation(libs.android.database.sqlcipher)

    implementation(libs.tink.android)

    implementation(libs.jsoup)

    // Orbit MVI/MVVM+
    implementation(libs.orbit.core)
    implementation(libs.orbit.viewmodel)
    implementation(libs.orbit.compose)

    implementation(libs.ktor.client.websockets)

    implementation(libs.lottie.compose)

    implementation(libs.haze.materials)
    implementation(libs.haze)

    implementation(libs.exoplayer.core)
    implementation(libs.exoplayer.dash)
    implementation(libs.exoplayer.ui)

    implementation(libs.kotlinx.datetime)

    // Core KMP module
    implementation(libs.flowmvi.core)
    // compose multiplatform
    implementation(libs.flowmvi.compose)
    // saving and restoring state
    implementation(libs.flowmvi.savedstate)
    // testing DSL
    testImplementation(libs.flowmvi.test)
    // android integration
    implementation(libs.flowmvi.android)
    // remote debugging client
    debugImplementation(libs.flowmvi.debugger.plugin)

    implementation(libs.play.services.location)

    implementation(libs.molecule.runtime)

    implementation(libs.timber)

    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.coil.compose)
    implementation(libs.coil)
    implementation(libs.coil.gif)
    implementation(libs.coil.svg)
    implementation(libs.coil.video)
    implementation(libs.coil.svg)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.appyx.navigation.android)
    implementation(libs.appyx.navigation.android)
    ksp(libs.appyx.processor)
    implementation(libs.backstack.android)
    implementation(libs.spotlight.android)

    implementation(libs.jakarta.inject.api)

    debugImplementation(libs.utils.testing.ui.activity)
    androidTestImplementation(libs.utils.testing.ui)

    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    testImplementation(libs.orbit.test)

    testImplementation(libs.truth)

    testImplementation(libs.turbine)

    testImplementation(libs.androidx.paging.common.ktx)

    testImplementation(libs.coil.test)

    testImplementation(libs.konsist)

    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.property)

    testImplementation(libs.robolectric)

    testImplementation(libs.roborazzi)
    testImplementation(libs.roborazzi.compose)

    androidTestImplementation(libs.androidx.work.testing)
    androidTestImplementation(platform(libs.compose.bom))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
