// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.ktorfit) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.dependencygraph) apply false
    alias(libs.plugins.dependencycheck) apply false
    alias(libs.plugins.androidTest) apply false
}

buildscript {
    dependencies {
        classpath(libs.secrets.gradle.plugin)
    }
}
